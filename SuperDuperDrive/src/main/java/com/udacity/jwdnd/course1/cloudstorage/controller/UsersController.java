package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersController {

	@Autowired
	private UserServices userServices;

	@GetMapping("/login")
	public ModelAndView login() {

		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return new ModelAndView("login.html");
		}
		return new ModelAndView("redirect:/home");
	}

	@GetMapping("/signup")
	public ModelAndView signup() {
		ModelAndView mva = new ModelAndView("signup.html");
		return mva;
	}

	@PostMapping("/signup")
	public ModelAndView register(Users user, Model model) {

		ModelAndView mav = new ModelAndView("redirect:/login");

		String errorMessage = "";
		if (StringUtils.isEmpty(errorMessage = userServices.validation(user))) {

				boolean resultF = userServices.add(user);
			if (resultF) {
				mav = new ModelAndView("redirect:/login?ok");
			} else {
				mav = new ModelAndView("result.html");
				mav.addObject("ngFlag", "Sign up user failure!");
			}
		} else {
			mav = new ModelAndView("result.html");
			mav.addObject("ngFlag", errorMessage);
		}
		return mav;
	}
}
