/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.registry.jira.issues.test;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.carbon.automation.engine.context.TestUserMode;
import org.wso2.carbon.registry.search.metadata.test.bean.SearchParameterBean;
import org.wso2.carbon.registry.search.stub.SearchAdminServiceRegistryExceptionException;
import org.wso2.carbon.registry.search.stub.beans.xsd.AdvancedSearchResultsBean;
import org.wso2.carbon.registry.search.stub.beans.xsd.ArrayOfString;
import org.wso2.carbon.registry.search.stub.beans.xsd.CustomSearchParameterBean;
import org.wso2.greg.integration.common.clients.SearchAdminServiceClient;
import org.wso2.greg.integration.common.utils.GREGIntegrationBaseTest;

import java.rmi.RemoteException;

/**
 * https://wso2.org/jira/browse/CARBON-13084   reopened
 */

public class Carbon13084TestCase extends GREGIntegrationBaseTest {


    private SearchAdminServiceClient searchAdminServiceClient;

    @BeforeClass
    public void init() throws Exception {
        super.init(TestUserMode.SUPER_TENANT_ADMIN);
        String sessionCookie = getSessionCookie();

        searchAdminServiceClient =
                new SearchAdminServiceClient(backendURL, sessionCookie);

    }

    @Test(groups = {"wso2.greg"}, description = "search by tag with value comma separator")
    public void testSearchByTag()
            throws SearchAdminServiceRegistryExceptionException, RemoteException {

        CustomSearchParameterBean searchQuery = new CustomSearchParameterBean();
        SearchParameterBean paramBean = new SearchParameterBean();
        paramBean.setTags(",");
        ArrayOfString[] paramList = paramBean.getParameterList();
        searchQuery.setParameterValues(paramList);
        paramList = null;
        AdvancedSearchResultsBean result = null;

        try {
            result = searchAdminServiceClient.getAdvancedSearchResults(searchQuery);
        } finally {
            assert result != null;
            if (result.getResourceDataList()[0] == null) {
                Assert.assertNull(result.getResourceDataList()[0], "No results returned");

            } else {
                Assert.assertNull(result.getResourceDataList()[0], "Result Object found.");
            }
        }

    }

    @AfterClass
    public void testCleanup() {
        searchAdminServiceClient = null;
    }
}