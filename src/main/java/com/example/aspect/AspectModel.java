package com.example.aspect;

import java.time.LocalTime;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
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

import com.example.Allocations.AllocationsService;
import com.example.BankBranches.BrancheService;
import com.example.Banks.BankService;
import com.example.Clients.ClientService;
import com.example.CloseLoans.CloseLoanService;
import com.example.Loans.LoanService;
import com.example.OpenLoans.OpenLoansService;
import com.example.ReScheduleLoans.ReScheduleLoansService;
import com.example.SiteConfig.SiteConfigController;
import com.example.SiteConfig.SiteConfiguration;
import com.example.Vouchers.VoucherService;
import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.PermissionsService;
import com.example.security.roles.RolesService;
import com.example.security.rolesPermissions.RolesPermissionsService;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import com.example.security.user.UserService;
import com.example.security.userPermissions.UserPermissionsService;
import com.example.settelmets.Services.SettlementService;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AspectModel {
	
	@Autowired
	private UserRepository userRepo ; 

	//Controlling access to site routes  
	
	/*
	@Before("execution(* com.example.security.user.UserController..*(..)))")
	public void secureUserService(JoinPoint  proceedingJoinPoint)  {
			System.out.println("intercepting user Controller methods ");
			printFunctionCallInfo(proceedingJoinPoint);
			//if(!checkEntryTime()) {
				//throw new OutOfDayBoundsException() ; 
			//}
			//User user = get_current_User();    
			//user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
	
	@Before("execution(* com.example.Banks.BankController..*(..)))")
	public void secureBanksService(JoinPoint  proceedingJoinPoint)  {
			//System.out.println("intercepting banks Controller ");
			//printFunctionCallInfo(proceedingJoinPoint);
			User user = get_current_User();    
			user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
		
	@Before("execution(* com.example.security.roles.RolesController..*(..)))")
	public void secureRolesService(JoinPoint  proceedingJoinPoint)  {
			System.out.println("intercepting ROLES Controller ");
			printFunctionCallInfo(proceedingJoinPoint);
			//User user = get_current_User();    
			//user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
	
	@Before("execution(* com.example.security.permissions.PermissionsController..*(..)))")
	public void securePermissionsService(JoinPoint  proceedingJoinPoint)  {
			System.out.println("intercepting Permissions Controller ");
			printFunctionCallInfo(proceedingJoinPoint);
			//User user = get_current_User();    
			//user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
	
	@Before("execution(* com.example.Clients.ClinetController..*(..)))")
	public void secureClientsService(JoinPoint  proceedingJoinPoint)  {
			System.out.println("intercepting Clients Controller ");
			printFunctionCallInfo(proceedingJoinPoint);
			//User user = get_current_User();    
			//user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
	
	@Before("execution(* com.example.BankBranches.BracheController..*(..)))")
	public void secureBranchesService(JoinPoint  proceedingJoinPoint)  {
			System.out.println("intercepting Branches Controller ");
			printFunctionCallInfo(proceedingJoinPoint);
			//User user = get_current_User();    
			//user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
	
	@Before("execution(* com.example.Allocations.AllocationsController..*(..)))")
	public void secureAllocationsService(JoinPoint  proceedingJoinPoint)  {
			System.out.println("intercepting Allocations Controller ");
			printFunctionCallInfo(proceedingJoinPoint);
			//User user = get_current_User();    
			//user.flatUserDetailes();   
			//checkUserPermission(proceedingJoinPoint,user);
	}
	
	@Before("execution(* com.example.Vouchers.VoucherController..*(..)))")
	public void secureVouchersService() {
		if(!checkEntryTime()) {
			throw new OutOfDayBoundsException() ; 
		}
	}
	
	@Before("execution(* com.example.Loans.LoansController..*(..)))")
	public void secureLoansService() {
		if(!checkEntryTime()) {
			throw new OutOfDayBoundsException() ; 
		}
	}
	
	
	//logging 
	Logger UserLogger =LoggerFactory.getLogger(UserService.class);
	Logger SettlementLogger = LoggerFactory.getLogger(SettlementService.class);
	Logger AllocationsLogger = LoggerFactory.getLogger(AllocationsService.class);
	Logger BranchesLogger = LoggerFactory.getLogger(BrancheService.class);
	Logger BanksLogger = LoggerFactory.getLogger(BankService.class);
	Logger ClientsLogger = LoggerFactory.getLogger(ClientService.class);
	Logger ClosedLoansLogger = LoggerFactory.getLogger(CloseLoanService.class);
	Logger LoansLogger = LoggerFactory.getLogger(LoanService.class);
	Logger OpenLoansLogger = LoggerFactory.getLogger(OpenLoansService.class);
	Logger ResLoansLogger = LoggerFactory.getLogger(ReScheduleLoansService.class);
	Logger SiteConfigLogger = LoggerFactory.getLogger(SiteConfigController.class);
	Logger PermissionsLogger = LoggerFactory.getLogger(PermissionsService.class);
	Logger RolesLogger = LoggerFactory.getLogger(RolesService.class);
	Logger RolePermissionLogger = LoggerFactory.getLogger(RolesPermissionsService.class);
	Logger UserServiceLogger = LoggerFactory.getLogger(UserService.class);
	Logger UserPermissionsLogger = LoggerFactory.getLogger(UserPermissionsService.class);
	Logger UserRolesLogger = LoggerFactory.getLogger(UserRoleService.class);
	Logger VouchersLogger = LoggerFactory.getLogger(VoucherService.class);
	
	
	@After("execution(* com.example.Allocations.AllocationsService..*(..))")
	public void logAllocationsService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        AllocationsLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}
	
	@After("execution(* com.example.BankBranches.BrancheService..*(..))")
	public void logBranchesService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	BranchesLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	
	@After("execution(* com.example.Banks.BankService..*(..))")
	public void logBanksService(JoinPoint  proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	BanksLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.Clients.ClientService ..*(..))")
	public void logClientsService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	ClientsLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.CloseLoans.CloseLoanService..*(..))")
	public void logCloseLoansService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	ClosedLoansLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.Loans.LoanService..*(..))")
	public void logLoansService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	LoansLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.OpenLoans.OpenLoansService..*(..))")
	public void logOpenLoansService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	OpenLoansLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.ReScheduleLoans.ReScheduleLoansService..*(..))")
	public void logReScheduleLoansService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	ResLoansLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.SiteConfig.SiteConfigController..*(..))")
	public void logSiteConfigService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	SiteConfigLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.security.permissions.PermissionsService..*(..))")
	public void logpermissionsService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	PermissionsLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.security.roles.RolesService..*(..))")
	public void logRolesService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	RolesLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.security.rolesPermissions.RolesPermissionsService..*(..))")
	public void logrolesPermissionsService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	RolePermissionLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.security.user.UserService..*(..))")
	public void logUserService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	UserServiceLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.security.userPermissions.UserPermissionsService..*(..))")
	public void loguserPermissionsService(JoinPoint proceedingJoinPoint) {
        User currUser = get_current_User();
        if(currUser != null )
        	UserPermissionsLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.security.UserRoles.UserRoleService..*(..))")
	public void logUserRolesService(JoinPoint proceedingJoinPoint) {
	        User currUser = get_current_User();
	        if(currUser != null )
	        	UserRolesLogger.info("user : "+currUser.getUsername()+" started Execution of " 
	        + printFunctionCallInfo(proceedingJoinPoint));
	}

	@After("execution(* com.example.Vouchers.VoucherService..*(..))")
	public void logVouchersService(JoinPoint proceedingJoinPoint)  {
        User currUser = get_current_User();
        if(currUser != null )
        	VouchersLogger.info("user : "+currUser.getUsername()+" started Execution of " 
        + printFunctionCallInfo(proceedingJoinPoint));
	}

	
	//end of logging 
	
	@Around("execution(* com.example.settelmets.SettlementService..*(..))")
    public Object SettlementServiceAspectHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        User currUser = get_current_User();
        if(currUser != null )
        SettlementLogger.info("user : "+currUser.getUsername()+" started Execution of " + printFunctionCallInfo(proceedingJoinPoint)+" with excution Time ::" + stopWatch.getTotalTimeMillis() + " ms");
        return result;
    }
	
	
	
	
	//service Methods 
	
	private User get_current_User() {
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

	private List<String> getUserPermissions(User user ){
		List<String> permissions =  user.convertPermissionsToList();
		return permissions ; 
	}

	private void checkUserPermission(JoinPoint  proceedingJoinPoint , User user ) {
		if(user.convertRolesToList().contains("SUPER")) {
			return ; 
		}
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName();
        if(!getUserPermissions(user).contains(methodName)) {
			throw new UnAuthorizedException(); 
		}
	}
	
	private String printFunctionCallInfo(JoinPoint  proceedingJoinPoint) {
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
		
	private boolean checkEntryTime() {
		LocalTime LT = LocalTime.now();
		int hour = LT.getHour() ; 
		int sHour = SiteConfiguration.getDayStartHour() ; 
		int EHour = SiteConfiguration.getDayEndHour() ; 
		System.out.println("curr time : "+hour);
		System.out.println("start hour : "+sHour);
		System.out.println("end hour : "+EHour);
		if(hour >= SiteConfiguration.getDayStartHour()) {
			if(hour < SiteConfiguration.getDayEndHour()) {
				return true; 
			}
		}
		return false ; 
	}
	
	
	*/
}


