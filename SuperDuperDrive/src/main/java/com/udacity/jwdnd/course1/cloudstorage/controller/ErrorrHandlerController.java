package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorrHandlerController implements ErrorController {

    @GetMapping("/error")
    public ModelAndView customError() {
        ModelAndView mav = new ModelAndView("result.html");
        mav.addObject("ngFlag", "Page not found!!!.");
        return mav;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}