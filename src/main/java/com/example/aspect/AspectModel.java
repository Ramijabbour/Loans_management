package com.example.aspect;

import java.util.List;

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
import org.springframework.util.StopWatch;
import org.aspectj.lang.reflect.MethodSignature;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import com.example.security.user.UserService;
import com.example.settelmets.SettlementService;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AspectModel {
	
	@Autowired
	private UserRepository userRepo ; 
	
	Logger UserLogger =LoggerFactory.getLogger(UserService.class);
	Logger SettlementLogger = LoggerFactory.getLogger(SettlementService.class);
	
	@Before("execution(* com.example.security.user.UserService..*(..)))")
	public void test(JoinPoint  proceedingJoinPoint)  {
			System.out.println("intercepting user Service methods ");
			printFunctionCallInfo(proceedingJoinPoint);
			//User user = get_current_User();    
			//user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
	
	@Around("execution(* com.example.settelmets.SettlementService..*(..))")
    public Object SettlementServiceAspectHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        final StopWatch stopWatch = new StopWatch();
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        //Log method execution time
        SettlementLogger.info("Execution of " + printFunctionCallInfo(proceedingJoinPoint)+" with excution Time ::" + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }
	
	public User get_current_User() {
		String username ; 
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Object principal =  auth.getPrincipal();
	        if(principal instanceof UserDetails) {
	        	 username = ((UserDetails) principal).getUsername() ; 
		         for(User user : this.userRepo.findAll()) {
		 			if(user.getUsername().equalsIgnoreCase(username)) {
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

	public List<String> getUserPermissions(User user ){
		List<String> permissions =  user.convertPermissionsToList();
		return permissions ; 
	}

	public void checkUserPermission(JoinPoint  proceedingJoinPoint , User user ) {
		if(user.convertRolesToList().contains("SUPER")) {
			return ; 
		}
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName();
        if(!getUserPermissions(user).contains(methodName)) {
			throw new UnAuthorizedException(); 
		}
	}

	
	public String printFunctionCallInfo(JoinPoint  proceedingJoinPoint) {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        String[] param = methodSignature.getParameterNames();
       String out = "excution request for : " + className + "." + methodName ; 
        System.out.println("excution request for : " + className + "." + methodName );
        for(String parameter : param ) {
        	out+="with parameter : "+parameter ; 
        	System.out.println("with parameter : "+parameter);
        }
        return out ; 
	}
	
}


