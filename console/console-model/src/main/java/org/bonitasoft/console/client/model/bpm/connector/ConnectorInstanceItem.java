/**
 * Copyright (C) 2012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.console.client.model.bpm.connector;

import org.bonitasoft.web.toolkit.client.data.APIID;
import org.bonitasoft.web.toolkit.client.data.item.Definitions;
import org.bonitasoft.web.toolkit.client.data.item.Item;
import org.bonitasoft.web.toolkit.client.data.item.ItemDefinition;
import org.bonitasoft.web.toolkit.client.data.item.template.ItemHasUniqueId;

/**
 * @author Vincent Elcrin
 * 
 */
public class ConnectorInstanceItem extends Item implements ItemHasUniqueId {

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ATTRIBUTES
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String ATTRIBUTE_CONNECTOR_ID = "connectorId";

    public static final String ATTRIBUTE_NAME = "name";

    public static final String ATTRIBUTE_VERSION = "version";

    public static final String ATTRIBUTE_ACTIVATION_EVENT = "activationEvent";

    public static final String ATTRIBUTE_STATE = "state";

    public static final String ATTRIBUTE_CONTAINER_TYPE = "containerType";

    public static final String ATTRIBUTE_CONTAINER_ID = "containerId";

    public static final String ATTRIBUTE_RESET_STATE = "resetState";

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ATTRIBUTES VALUES
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String VALUE_ACTIVATION_EVENT_ON_ENTER = "ON_ENTER";

    public static final String VALUE_ACTIVATION_EVENT_ON_FINISH = "ON_FINISH";

    public static final String VALUE_STATE_TO_BE_EXECUTED = "TO_BE_EXECUTED";

    public static final String VALUE_STATE_TO_RE_EXECUTE = "TO_RE_EXECUTE";

    public static final String VALUE_STATE_DONE = "DONE";

    public static final String VALUE_STATE_FAILED = "FAILED";

    public static final String VALUE_STATE_SKIPPED = "SKIPPED";

    public static final String VALUE_RESET_STATE_TO_RE_EXECUTE = "toReExecute";

    public static final String VALUE_RESET_STATE_SKIPPED = "skipped";

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FILTERS
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SETTERS AND GETTERS
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // SETTERS

    @Override
    public void setId(String id) {
        this.setAttribute(ATTRIBUTE_ID, id);
    }

    @Override
    public void setId(Long id) {
        this.setAttribute(ATTRIBUTE_ID, id);
    }

    public void setName(String name) {
        this.setAttribute(ATTRIBUTE_NAME, name);
    }

    public void setConnectorId(APIID id) {
        this.setAttribute(ATTRIBUTE_CONNECTOR_ID, id);
    }

    public void setConnectorId(String id) {
        this.setAttribute(ATTRIBUTE_CONNECTOR_ID, id);
    }

    public void setConnectorId(Long id) {
        this.setAttribute(ATTRIBUTE_CONNECTOR_ID, id);
    }

    public void setVersion(String version) {
        this.setAttribute(ATTRIBUTE_VERSION, version);
    }

    public void setActivationEvent(String activationEvent) {
        this.setAttribute(ATTRIBUTE_ACTIVATION_EVENT, activationEvent);
    }

    public void setState(String state) {
        this.setAttribute(ATTRIBUTE_STATE, state);
    }

    public void setContainerType(String type) {
        this.setAttribute(ATTRIBUTE_CONTAINER_TYPE, type);
    }

    public void setContainerId(APIID id) {
        this.setAttribute(ATTRIBUTE_CONTAINER_ID, id);
    }

    public void setContainerId(String id) {
        this.setAttribute(ATTRIBUTE_CONTAINER_ID, id);
    }

    public void setContainerId(Long id) {
        this.setAttribute(ATTRIBUTE_CONTAINER_ID, id);
    }

    // GETTERS

    public String getName() {
        return this.getAttributeValue(ATTRIBUTE_NAME);
    }

    public APIID getConnectorId() {
        return this.getAttributeValueAsAPIID(ATTRIBUTE_CONNECTOR_ID);
    }

    public String getVersion() {
        return this.getAttributeValue(ATTRIBUTE_VERSION);
    }

    public String getActivationEvent() {
        return this.getAttributeValue(ATTRIBUTE_ACTIVATION_EVENT);
    }

    public String getState() {
        return this.getAttributeValue(ATTRIBUTE_STATE);
    }

    public APIID getContainerId() {
        return this.getAttributeValueAsAPIID(ATTRIBUTE_CONTAINER_ID);
    }

    public String getContainerType() {
        return this.getAttributeValue(ATTRIBUTE_CONTAINER_TYPE);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DEPLOYS
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // UTILS
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public ItemDefinition getItemDefinition() {
        return Definitions.get(ConnectorInstanceDefinition.TOKEN);
    }

}
