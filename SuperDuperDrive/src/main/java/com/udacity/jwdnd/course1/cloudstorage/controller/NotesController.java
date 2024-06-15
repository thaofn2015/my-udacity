package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUtil;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {

	@Autowired
	private NotesServices notesServices;

	@PostMapping("/save")
	public ModelAndView save(Authentication authentication, Notes note) {

		ModelAndView mav = new ModelAndView("result.html");

		Integer userId = ((Users) authentication.getPrincipal()).getUserId();

		note.setUserId(userId);

		boolean resultF = false;

		if (!notesServices.isExist(note.getNoteId(), userId)) {
			resultF = notesServices.add(note);
		} else {
			resultF = notesServices.update(note);
		}

		if (resultF) {
			mav.addObject("okFlag", "Your changes were successfully saved.");
		} else {
			mav.addObject("ngFlag", "Your changes are not saved");
		}
		return mav;
	}

	@GetMapping("/delete")
	public ModelAndView delete(@RequestParam("noteId")Integer noteId, Authentication authentication) {

		ModelAndView mav = new ModelAndView("result.html");

		if (noteId != null) {

			Integer userId = ((Users) authentication.getPrincipal()).getUserId();
			boolean resultF = notesServices.delete(noteId, userId);
			if (resultF) {
				mav.addObject("okFlag", "Your changes were successfully saved.");
			} else {
				mav.addObject("ngFlag", "Your changes are not saved");
			}
		} else {
			mav.addObject("ngFlag", "Error system");
		}

		return mav;
	}
}
