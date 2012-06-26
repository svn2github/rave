/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.rave.portal.model;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public interface OAuthTokenInfo {
    /**
     * @see {@link org.apache.shindig.social.core.oauth.OAuthSecurityToken#getModuleId()}
     */
    public static final String MODULE_ID = "NOT_USED";

    Long getId();
    void setId(Long id);

    String getAccessToken();
    void setAccessToken(String accessToken);

    String getTokenSecret();
    void setTokenSecret(String tokenSecret);

    String getSessionHandle();
    void setSessionHandle(String sessionHandle);

    long getTokenExpireMillis();
    void setTokenExpireMillis(long tokenExpireMillis);

    String getAppUrl();
    void setAppUrl(String appUrl);

    String getModuleId();
    void setModuleId(String moduleId);

    String getServiceName();
    void setServiceName(String serviceName);

    String getTokenName();
    void setTokenName(String tokenName);

    String getUserId();
    void setUserId(String userId);
}
