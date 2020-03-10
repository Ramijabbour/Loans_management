package com.example.aspect;

import java.io.IOException;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

import com.example.security.user.User;
import com.example.security.user.UserRepository;
import com.example.security.user.UserService;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AspectModel {
	
	@Autowired
	private UserRepository userRepo ; 
	
	Logger UserLogger =LoggerFactory.getLogger(UserService.class);
	
	@Before("execution(* com.example.security.user.UserService..*(..)))")
	public void test(JoinPoint  proceedingJoinPoint)  {
		 	MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
	        String className = methodSignature.getDeclaringType().getSimpleName();
	        String methodName = methodSignature.getName();
	        System.out.println("excution request for : " + className + "." + methodName );
	       
	        User u = get_current_User();    
			u.flatUserDetailes();
	        
			
	}
	
	public User get_current_User() {
		String username ; 
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Object principal =  auth.getPrincipal();
	        if(principal instanceof UserDetails) {
	        	 username = ((UserDetails) principal).getUsername() ; 
		         for(User user : this.userRepo.findAll()) {
		 			if(user.getUserName().equalsIgnoreCase(username)) {
		 				return user ; 
		 			}
		 		}
	        }
	        else {
	        	System.out.println((String)principal + "attempted route access ");
	        	System.out.println("Access intercepted from Aspect");
	        	throw new UnAuthenticatedException();
	        }
	         return null  ; 
    }

}


