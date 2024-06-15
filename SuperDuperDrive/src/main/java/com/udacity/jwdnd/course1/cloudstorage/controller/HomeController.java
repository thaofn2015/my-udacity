package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsServices;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesServices;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@Autowired
	private NotesServices notesServices;

	@Autowired
	private CredentialsServices credentialsServices;

	@Autowired
	private FilesServices filesServices;

	@GetMapping(value = { "/home", "/" })
	public ModelAndView index(Authentication authentication) {
		Integer userId = ((Users) authentication.getPrincipal()).getUserId();

		ModelAndView mva = new ModelAndView("home.html");

		mva.addObject("files", filesServices.findAll(userId));
		mva.addObject("notes", notesServices.getAll(userId));
		mva.addObject("credentials", credentialsServices.getAll(userId));

		return mva;
	}
}
