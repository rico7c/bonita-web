/*
 * Copyright (C) 2014 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */



import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.doReturn

import org.bonitasoft.engine.api.APIAccessor
import org.bonitasoft.engine.api.Logger
import org.bonitasoft.engine.api.permission.APICallContext
import org.bonitasoft.engine.api.permission.PermissionRule
import org.bonitasoft.engine.session.APISession
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.class)
public class UserPermissionRuleTest {
    @Mock
    def APISession apiSession
    @Mock
    def APICallContext apiCallContext
    @Mock
    def APIAccessor apiAccessor
    @Mock
    def Logger logger
    def PermissionRule rule = new UserPermissionRule();

    @Test
    public void checkOnResourceWithCurrentUser() throws Exception {
        doReturn(15l).when(apiSession).getUserId()
        doReturn("15").when(apiCallContext).getResourceId()

        def isAuthorized = rule.isAllowed(apiSession, apiCallContext, apiAccessor, logger)

        assertThat(isAuthorized).isTrue()
    }

    @Test
    public void checkOnResourceWithOtherCurrentUser() throws Exception {
        doReturn(15l).when(apiSession).getUserId()
        doReturn("16").when(apiCallContext).getResourceId()

        def isAuthorized = rule.isAllowed(apiSession, apiCallContext, apiAccessor, logger)

        assertThat(isAuthorized).isFalse()
    }

    @Test
    public void checkOnResourceWithDeployOnPersonnalData() throws Exception {
        doReturn(15l).when(apiSession).getUserId()
        doReturn("16").when(apiCallContext).getResourceId()
        doReturn("?d=personnal_data&otherParam=2").when(apiCallContext).getQueryString()

        def isAuthorized = rule.isAllowed(apiSession, apiCallContext, apiAccessor, logger)

        assertThat(isAuthorized).isFalse()
    }

    @Test
    public void checkOnResourceWithDeployOnProfessionalData() throws Exception {
        doReturn(15l).when(apiSession).getUserId()
        doReturn("16").when(apiCallContext).getResourceId()
        doReturn("?d=professional_data&otherParam=2").when(apiCallContext).getQueryString()

        def isAuthorized = rule.isAllowed(apiSession, apiCallContext, apiAccessor, logger)

        assertThat(isAuthorized).isFalse()
    }

    @Test
    public void checkOnResourceWithNoResource() throws Exception {
        doReturn(15l).when(apiSession).getUserId()
        doReturn("?otherParam=2").when(apiCallContext).getQueryString()

        def isAuthorized = rule.isAllowed(apiSession, apiCallContext, apiAccessor, logger)

        assertThat(isAuthorized).isTrue()
    }
}
