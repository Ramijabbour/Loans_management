package com.example.aspect;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsHandler {
	
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public ModelAndView handle_not_found(){
		System.out.println("not found invoked ");
		ModelAndView mav = new ModelAndView("Login/login");
		return mav; 
	}
	
	
	@ExceptionHandler(value = UnAuthenticatedException.class)
	public ModelAndView handleExceptions(){
		System.out.println("Exceptions handlerinvoked ");
		ModelAndView mav = new ModelAndView("exceptions/exception");
		mav.addObject("cerror",new UnAuthenticatedException());
		return mav ; 
	}

	@ExceptionHandler(value = UnAuthorizedException.class)
	public ModelAndView UnAuthorizedException(){
		System.out.println("Exceptions handlerinvoked ");
		ModelAndView mav = new ModelAndView("exceptions/exception");
		mav.addObject("cerror",new UnAuthorizedException());
		return mav ; 
	}
	
}