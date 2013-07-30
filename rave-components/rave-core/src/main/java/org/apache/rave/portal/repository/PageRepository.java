/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.rave.portal.repository;

import org.apache.rave.repository.Repository;
import org.apache.rave.model.*;


import java.util.List;

/**
 * Provides persistence operations for the {@link org.apache.rave.model.Page} class
 */
public interface PageRepository extends Repository<Page>{
    /**
     * Returns all pages of a given PageType owned by the user
     *
     * @param userId the userId to search by
     * @param pageType the pageType to search by
     * @return a list of all the Page objects owned by userId of type pageType
     */
    List<Page> getAllPagesForUserType(String userId, PageType pageType);

    /**
     * Delete all pages for a userId of the supplied pageType
     * @param userId
     * @param pageType
     * @return the number of pages deleted
     */
    int deletePages(String userId, PageType pageType);

    /**
     * Create either a Person or User Page from PageTemplate for the given user
     * @param user User
     * @param pt PageTemplate
     * @return Page
     */
    Page createPageForUser(User user, PageTemplate pt);

    /**
     * Determine if the given user has a person page
     *
     * @param userId
     * @return boolean
     */
    boolean hasPersonPage(String userId);

    /**
     * Returns a list of pageUser records of a certain pageType.
     * Used to get a list of a users pages(user) with render sequencing.
     * @param userId
     * @param pageType
     * @return a list of pageUser
     */
    public List<PageUser> getPagesForUser(String userId, PageType pageType);


    /**
     * Returns a single pageUser tuple based on userId and pageId
     * @param userId
     * @param pageId
     * @return a single pageUser
     */
    public PageUser getSingleRecord(String userId, String pageId);
}
