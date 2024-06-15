package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CommonUtil;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FilesController {

	@Autowired
	private FilesServices filesServices;

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException e, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("result.html");

		mav.addObject("ngFlag", "File must not exceed 100MB");

		return mav;
	}

	@PostMapping("/save")
	public ModelAndView save(Authentication authentication, MultipartFile fileUpload) throws IOException, MaxUploadSizeExceededException {

		ModelAndView mav = new ModelAndView("result.html");

		Integer userId = ((Users) authentication.getPrincipal()).getUserId();

		String fileName = CommonUtil.getFileName(fileUpload);

		if (fileUpload.isEmpty()) {
			mav.addObject("ngFlag", "No files selected");
		} else if (filesServices.isDuplicateName(fileName, userId)) {
			mav.addObject("ngFlag", "Duplicate file name");
		} else {

			File file = new File(null,
					                 fileName,
									 fileUpload.getContentType(),
					                 fileUpload.getSize() + "",
									 userId,
					                 fileUpload.getBytes());

			boolean resultF = filesServices.save(file, userId);
			if (resultF) {
				mav.addObject("okFlag", "Your changes were successfully saved.");
			} else {
				mav.addObject("ngFlag", "Your changes are not saved");
			}
		}
		return mav;
	}

	@GetMapping("/delete")
	public ModelAndView delete(@RequestParam("fileId") Integer fileid, Authentication authentication) {

		ModelAndView mav = new ModelAndView("result.html");

		if (fileid != null) {

			Integer userId = ((Users) authentication.getPrincipal()).getUserId();
			boolean resultF  = filesServices.deleteFileById(fileid, userId);
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
	public ResponseEntity<byte[]> view(@RequestParam("fileId") Integer fileId, Authentication authentication) {

		Integer userId = ((Users) authentication.getPrincipal()).getUserId();

		File file = filesServices.get(fileId, userId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", file.getFileName());
		headers.setContentType(MediaType.parseMediaType(file.getContentType()));

		return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
	}
}
