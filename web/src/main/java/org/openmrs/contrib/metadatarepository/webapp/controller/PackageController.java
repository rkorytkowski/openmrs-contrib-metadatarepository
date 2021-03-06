/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.contrib.metadatarepository.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.contrib.metadatarepository.Constants;
import org.openmrs.contrib.metadatarepository.model.User;
import org.openmrs.contrib.metadatarepository.service.PackageManager;
import org.openmrs.contrib.metadatarepository.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PackageController {

	private PackageManager magr = null;
	private UserManager umagr = null;

	@Autowired
	public void setUmagr(UserManager umagr) {
		this.umagr = umagr;
	}

	@Autowired
	public void setPackageManager(PackageManager packagemanager) {
		this.magr = packagemanager;
	}

	@RequestMapping(value = "/mainMenu*", method = RequestMethod.GET)
	public ModelAndView handleRequest(
			@RequestParam(required = false, value = "q") String query)
			throws Exception {
		return new ModelAndView("/mainMenu", Constants.PACKAGE_LIST,
				magr.search(query));
	}

	@RequestMapping(value = "/mypackages*", method = RequestMethod.GET)
	public ModelAndView myPackages(
			@RequestParam(required = false, value = "q") String query,
			HttpServletRequest request) throws Exception {
		User user = umagr.getUserByUsername(request.getRemoteUser());
		return new ModelAndView("/mypackages", Constants.PACKAGE_LIST,
				magr.searchByUser(query, user));
	}

}
