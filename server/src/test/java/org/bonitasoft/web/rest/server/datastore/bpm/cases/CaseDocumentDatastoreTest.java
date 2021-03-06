package org.bonitasoft.web.rest.server.datastore.bpm.cases;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.console.common.server.preferences.constants.WebBonitaConstantsUtils;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.document.Document;
import org.bonitasoft.engine.bpm.document.DocumentNotFoundException;
import org.bonitasoft.engine.bpm.document.DocumentValue;
import org.bonitasoft.engine.exception.DeletionException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.search.SearchOptions;
import org.bonitasoft.engine.search.SearchResult;
import org.bonitasoft.engine.session.APISession;
import org.bonitasoft.web.rest.model.bpm.cases.CaseDocumentItem;
import org.bonitasoft.web.rest.server.APITestWithMock;
import org.bonitasoft.web.toolkit.client.common.exception.api.APIException;
import org.bonitasoft.web.toolkit.client.data.APIID;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CaseDocumentDatastoreTest extends APITestWithMock {

    private CaseDocumentDatastore documentDatastore;

    @Mock
    private WebBonitaConstantsUtils constantsValue;

    @Mock
    private APISession engineSession;

    @Mock
    private ProcessAPI processAPI;

    @Mock
    private Document mockedDocument;

    @Mock
    private SearchResult<Document> mockedEngineSearchResults;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private final CaseDocumentItem mockedDocumentItem = new CaseDocumentItem();

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        System.setProperty("bonita.home", "target/bonita-home/bonita");
        when(engineSession.getTenantId()).thenReturn(1L);
        when(mockedDocument.getName()).thenReturn("Doc 1");
        when(mockedDocument.getId()).thenReturn(1L);
        documentDatastore = spy(new CaseDocumentDatastore(engineSession, constantsValue, processAPI));
    }

    // ---------- GET METHOD TESTS ------------------------------//

    @Test
    public void it_should_call_engine_processAPI_getDocument() throws Exception {
        // Given
        final APIID id = APIID.makeAPIID(1l);

        // When
        documentDatastore.get(id);

        // Then
        verify(processAPI).getDocument(id.toLong());
    }

    @Test(expected = APIException.class)
    public void it_should_catch_and_throw_APIException_for_not_find_document() throws Exception {
        // Given
        final APIID id = APIID.makeAPIID(1l);
        when(processAPI.getDocument(id.toLong())).thenThrow(new DocumentNotFoundException("not found", new Exception()));

        // When
        documentDatastore.get(id);
    }

    @Test
    public void it_should_call_convertEngineToConsole_method() throws Exception {
        // Given
        final APIID id = APIID.makeAPIID(1l);

        // When
        documentDatastore.get(id);

        // Then
        verify(documentDatastore).convertEngineToConsoleItem(any(Document.class));
    }

    // ---------- CONVERT ITEM TESTS ------------------------------//

    @Test
    public void it_should_convert_item_return_item() throws Exception {
        // When
        final CaseDocumentItem convertedEngineToConsoleItem = documentDatastore.convertEngineToConsoleItem(mockedDocument);
        // Then
        assertTrue(convertedEngineToConsoleItem != null);
    }

    @Test
    public void it_should_not_convert_null_item_return_null() {
        // When
        final CaseDocumentItem convertedEngineToConsoleItem = documentDatastore.convertEngineToConsoleItem(null);
        // Then
        assertTrue(convertedEngineToConsoleItem == null);
    }

    // ---------- ADD METHOD TESTS ------------------------------//

    @Test
    public void it_should_add_a_document_calling_addDocument_with_upload_Path() throws Exception {
        // Given
        final URL docUrl = getClass().getResource("/doc.jpg");
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_CASE_ID, 1l);
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_NAME, "doc 1");
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_UPLOAD_PATH, docUrl.getPath());

        // When
        documentDatastore.add(mockedDocumentItem);

        // Then
        verify(documentDatastore).buildDocumentValueFromUploadPath(docUrl.getPath(), -1);
        verify(processAPI).addDocument(eq(1L), eq("doc 1"), eq(""), any(DocumentValue.class));
    }

    @Test
    public void it_should_add_a_document_calling_addDocument_with_upload_Path_with_index_and_description() throws Exception {
        // Given
        final URL docUrl = getClass().getResource("/doc.jpg");
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_CASE_ID, 1l);
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_NAME, "doc 1");
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_UPLOAD_PATH, docUrl.getPath());
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_DESCRIPTION, "This is a description");
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_INDEX, "2");

        // When
        documentDatastore.add(mockedDocumentItem);

        // Then
        verify(documentDatastore).buildDocumentValueFromUploadPath(docUrl.getPath(), 2);
        verify(processAPI).addDocument(eq(1L), eq("doc 1"), eq("This is a description"), any(DocumentValue.class));
    }

    @Test
    public void it_should_add_a_document_calling_addDocument_with_external_Url() throws Exception {
        // Given
        final URL docUrl = getClass().getResource("/doc.jpg");
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_CASE_ID, 1l);
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_NAME, "doc 1");
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_URL, "http://images/doc.jpg");

        // When
        documentDatastore.add(mockedDocumentItem);

        // Then
        verify(documentDatastore).buildDocumentValueFromUrl("http://images/doc.jpg", -1);
        verify(processAPI).addDocument(eq(1L), eq("doc 1"), eq(""), any(DocumentValue.class));
    }

    @Test(expected = APIException.class)
    public void it_throws_an_exception_adding_a_document_with_invalid_inputs() {
        // Given
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_CASE_ID, -1);
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_NAME, "");
        // byte[] fileContent = DocumentUtil.getArrayByteFromFile(new File(docUrl));
        // When
        documentDatastore.add(mockedDocumentItem);

    }

    @Test(expected = APIException.class)
    public void it_throws_an_exception_adding_a_document_with_missing_inputs() {
        // Given
        mockedDocumentItem.setAttribute(CaseDocumentItem.ATTRIBUTE_NAME, "");
        // byte[] fileContent = DocumentUtil.getArrayByteFromFile(new File(docUrl));
        // When
        documentDatastore.add(mockedDocumentItem);

    }

    // ---------- UPDATE METHOD TESTS ------------------------------//

    @Test
    public void it_should_update_a_document_calling_updateDocument_with_upload_Path() throws Exception {
        // Given
        final Map<String, String> attributes = new HashMap<String, String>();
        final String uploadPath = getClass().getResource("/doc.jpg").getPath();
        attributes.put(CaseDocumentItem.ATTRIBUTE_UPLOAD_PATH, uploadPath);

        // When
        documentDatastore.update(APIID.makeAPIID(1L), attributes);

        // Then
        verify(documentDatastore).buildDocumentValueFromUploadPath(uploadPath, -1);
        verify(processAPI).updateDocument(eq(1L), any(DocumentValue.class));
    }

    @Test
    public void it_should_update_a_document_calling_updateDocument_with_external_Url() throws Exception {
        // Given
        final Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(CaseDocumentItem.ATTRIBUTE_URL, "http://images/doc.jpg");

        // When
        documentDatastore.update(APIID.makeAPIID(1L), attributes);

        // Then
        verify(documentDatastore).buildDocumentValueFromUrl("http://images/doc.jpg", -1);
        verify(processAPI).updateDocument(eq(1L), any(DocumentValue.class));
    }

    @Test(expected = APIException.class)
    public void it_should_not_update_document_and_throws_exception_for_missing_uploadPath() {
        // Given
        final Map<String, String> attributes = new HashMap<String, String>();
        attributes.put(CaseDocumentItem.ATTRIBUTE_NAME, "Doc 1");
        final APIID id = APIID.makeAPIID(1l);
        try {
            when(processAPI.getDocument(1l)).thenReturn(mockedDocument);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        // When
        documentDatastore.update(id, attributes);
    }

    // ---------- SEARCH TESTS -------------------------------------------------//
    @Test
    public void it_should_call_buildSearchOptionCreator_method() throws SearchException {
        // Given
        when(processAPI.searchDocuments(any(SearchOptions.class))).thenReturn(mockedEngineSearchResults);
        final Map<String, String> filters = new HashMap<String, String>();
        filters.put("submittedBy", "1");

        // When
        documentDatastore.searchDocument(0, 10, "hello", filters, "name ASC");

        // Then
        verify(documentDatastore).buildSearchOptionCreator(0, 10, "hello", filters, "name ASC");
    }

    @Test
    public void it_should_call_processAPI_searchDocuments_method() throws SearchException {
        // Given
        when(processAPI.searchDocuments(any(SearchOptions.class))).thenReturn(mockedEngineSearchResults);
        final Map<String, String> filters = new HashMap<String, String>();
        filters.put("submittedBy", "1");

        // When
        documentDatastore.searchDocument(0, 10, "hello", filters, "name ASC");

        // Then
        verify(processAPI).searchDocuments(documentDatastore.searchOptionsCreator.create());
    }

    // -------------DELETE METHOD TESTS ------------------------------------------//
    @Test
    public void it_should_delete_one_document() throws DocumentNotFoundException, DeletionException {
        final List<APIID> docs = new ArrayList<APIID>();
        docs.add(APIID.makeAPIID(mockedDocument.getId()));

        // When
        documentDatastore.delete(docs);

        // Then
        verify(processAPI).removeDocument(1L);
        verify(processAPI, times(1)).removeDocument(any(Long.class));
    }

    @Test
    public void it_should_delete_two_documents() throws DocumentNotFoundException, DeletionException {
        final List<APIID> docs = new ArrayList<APIID>();
        docs.add(APIID.makeAPIID(mockedDocument.getId()));
        docs.add(APIID.makeAPIID(mockedDocument.getId()));

        // When
        documentDatastore.delete(docs);

        // Then
        verify(processAPI, times(2)).removeDocument(1L);
    }

    @Test
    public void it_should_throw_an_exception_when_input_is_null() throws DocumentNotFoundException, DeletionException {
        expectedEx.expect(APIException.class);
        expectedEx.expectMessage("Error while deleting a document. Document id not specified in the request");

        final List<APIID> docs = new ArrayList<APIID>();
        docs.add(APIID.makeAPIID(mockedDocument.getId()));

        // When
        documentDatastore.delete(null);
    }

    @Test
    public void it_should_throw_an_exception_when_document_is_not_found() throws DocumentNotFoundException, DeletionException {
        expectedEx.expect(APIException.class);
        expectedEx.expectMessage("Error while deleting a document. Document not found");
        // When
        when(processAPI.removeDocument(3L)).thenThrow(DocumentNotFoundException.class);

        final List<APIID> docs = new ArrayList<APIID>();
        docs.add(APIID.makeAPIID(3L));

        // When
        documentDatastore.delete(docs);
    }
}