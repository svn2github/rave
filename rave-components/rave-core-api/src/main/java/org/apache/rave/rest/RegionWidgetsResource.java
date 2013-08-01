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

package org.apache.rave.rest;

import org.apache.rave.rest.model.Page;
import org.apache.rave.rest.model.Region;
import org.apache.rave.rest.model.RegionWidget;
import org.apache.rave.rest.model.SearchResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface RegionWidgetsResource {

    /*
    --- RegionWidget Operations
     */

    /**
     * Returns the regionWidgets associated with a page and a region
     *
     * @return
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    SearchResult<RegionWidget> getPageRegionRegionWidgets();

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    RegionWidget createPageRegionRegionWidget(RegionWidget regionWidget);

    /**
     * Returns a regionWidget associated with a page and a region
     *
     * @param regionWidgetId the regionWidget id
     * @return
     */
    @GET
    @Path("/{regionWidgetId}")
    @Produces(MediaType.APPLICATION_JSON)
    RegionWidget getPageRegionRegionWidget(@PathParam("regionWidgetId") String regionWidgetId);

    /**
     * Updates a regionWidget associated with a page and a region
     *
     * @param regionWidgetId the regionWidget id
     * @param regionWidget   the new regionWidget definition
     * @return
     */
    @PUT
    @Path("/{regionWidgetId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    RegionWidget updatePageRegionRegionWidget(@PathParam("regionWidgetId") String regionWidgetId, RegionWidget regionWidget);

    /**
     * Deletes a regionWidget from a page region
     *
     * @param regionWidgetId the regionWidget id
     * @return
     */
    @DELETE
    @Path("/{regionWidgetId}")
    @Produces(MediaType.APPLICATION_JSON)
    RegionWidget deletePageRegionRegionWidget(@PathParam("regionWidgetId") String regionWidgetId);
}
