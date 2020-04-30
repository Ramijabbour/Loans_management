package com.DocumentTemplate;

import org.springframework.web.servlet.ModelAndView;

import com.example.settelmets.Models.SettledChaque;

public interface ViewDocTemplate {

    public ModelAndView ViewRTGSDOC(SettledChaque settledChaque);
}
