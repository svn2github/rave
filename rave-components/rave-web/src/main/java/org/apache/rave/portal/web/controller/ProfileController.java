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

package org.apache.rave.portal.web.controller;

import java.util.List;

import org.apache.rave.portal.model.Page;
import org.apache.rave.portal.model.Person;
import org.apache.rave.portal.model.User;
import org.apache.rave.portal.service.PageService;
import org.apache.rave.portal.service.UserService;
import org.apache.rave.portal.web.controller.util.ControllerUtils;
import org.apache.rave.portal.web.model.NavigationItem;
import org.apache.rave.portal.web.model.NavigationMenu;
import org.apache.rave.portal.web.model.UserForm;
import org.apache.rave.portal.web.util.ModelKeys;
import org.apache.rave.portal.web.util.ViewNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = {"/person/*", "/person"})
public class ProfileController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final UserService userService;
	private final PageService pageService;

	@Autowired
	public ProfileController(UserService userService, PageService pageService) {
		this.userService = userService;
        this.pageService = pageService;
	}

    /**
	 * Views the main page of another user's profile
	 *
     * @param userid			    userid 
     * @param model                 {@link Model} map
     * @param referringPageId		page reference id (optional)
	 * @return the view name of the user profile page
	 */
	
	@RequestMapping(value = {"/{userid:.*}"}, method = RequestMethod.GET)
	public String viewProfile(@PathVariable Long userid, ModelMap model, @RequestParam(required = false) Long referringPageId) {
		User user = userService.getUserById(userid);
		logger.debug("Viewing person profile for: " + user.getUsername());
		
		Page personProfilePage = pageService.getPersonProfilePage(user.getId());
        addAttributesToModel(model, user, referringPageId);
        model.addAttribute(ModelKeys.PAGE, personProfilePage);
		String view =  ViewNames.getPersonPageView(personProfilePage.getPageLayout().getCode());
        List<Person> friendRequests = userService.getFriendRequestsReceived(user.getUsername());
        addNavItemsToModel(view, model, referringPageId, user, friendRequests);
        return view;
	}
	/**
	 * Updates the user's personal information
	 *
	 * @param model           			{@link Model} map
	 * @param referringPageId			page reference id
	 * @param updatedUser				Updated user information
	 * @return the view name of the user profile page
	 */
    @RequestMapping(method = RequestMethod.POST)
    public String updateProfile(ModelMap model,
                                @RequestParam(required = false) Long referringPageId,
                                @ModelAttribute("updatedUser") UserForm updatedUser) {

        User user = userService.getAuthenticatedUser();
        logger.info("Updating " + user.getUsername() + " profile information");

        //set the updated fields for optional information
        user.setGivenName(updatedUser.getGivenName());
        user.setFamilyName(updatedUser.getFamilyName());
        user.setDisplayName(updatedUser.getDisplayName());
        user.setAboutMe(updatedUser.getAboutMe());
        user.setStatus(updatedUser.getStatus());
        user.setEmail(updatedUser.getEmail());

        //update the user profile
        userService.updateUserProfile(user);

        //set about tag page as default page for the changes to be visible
        addAttributesToModel(model, user, referringPageId);

        return ViewNames.REDIRECT + "app/person/" + user.getUsername();
    }

	/*
	 * Function to add attributes to model map
	 */
	private void addAttributesToModel(ModelMap model, User user, Long referringPageId) {
    	model.addAttribute(ModelKeys.USER_PROFILE, user);
    	model.addAttribute(ModelKeys.REFERRING_PAGE_ID, referringPageId);
    }

    public static void addNavItemsToModel(String view, ModelMap model, Long referringPageId, User user, List<Person> friendRequests) {
        long refPageId = referringPageId != null ? referringPageId : 0;
        final NavigationMenu topMenu = new NavigationMenu("topnav");

        NavigationItem friendRequestItems = new NavigationItem("page.profile.friend.requests", String.valueOf(friendRequests.size()) , "#");
        for(Person request : friendRequests) {
        	NavigationItem childItem = new NavigationItem((request.getDisplayName()!=null && !request.getDisplayName().isEmpty())? request.getDisplayName() : request.getUsername(), request.getUsername(), "#");
        	friendRequestItems.addChildNavigationItem(childItem);
        }
    	topMenu.addNavigationItem(friendRequestItems);
    	topMenu.getNavigationItems().addAll((ControllerUtils.getTopMenu(view, refPageId, user, false).getNavigationItems()));

    	model.addAttribute(topMenu.getName(), topMenu);
    }
}
