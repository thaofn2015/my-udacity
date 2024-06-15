package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUtil;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

	@Autowired
	private CredentialsServices credentialsServices;

	@PostMapping("/save")
	public ModelAndView save(Credentials credential, Authentication authentication) {

		ModelAndView mav = new ModelAndView("result.html");

		Integer userId = ((Users) authentication.getPrincipal()).getUserId();

		credential.setUserId(userId);

		boolean resultF = false;

		if (!credentialsServices.isExist(credential.getCredentialId(), userId)) {
			resultF = credentialsServices.add(credential);
		} else {
			resultF = credentialsServices.update(credential);
		}

		if (resultF) {
			mav.addObject("okFlag", "Your changes were successfully saved.");
		} else {
			mav.addObject("ngFlag", "Your changes are not saved");
		}

		return mav;
	}

	@GetMapping("/delete")
	public ModelAndView delete(@RequestParam("credentialId") Integer credentialId, Authentication authentication) {

		ModelAndView mav = new ModelAndView("result.html");

		if (credentialId != null) {

			Integer userId = ((Users) authentication.getPrincipal()).getUserId();
			boolean resultF = credentialsServices.delete(credentialId, userId);
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

	@GetMapping("/view")
	@ResponseBody
	public Credentials view(@RequestParam("credentialId") Integer credentialId, Authentication authentication) {

		Integer userId = ((Users) authentication.getPrincipal()).getUserId();

		if (credentialsServices.isExist(credentialId, userId)) {
			Credentials credential = credentialsServices.get(credentialId, userId);

			credential.setPassword(CommonUtil.decryptString(credential.getPassword(), credential.getKey()));

			return credential;
		} else {
			return null;
		}
	}
}
