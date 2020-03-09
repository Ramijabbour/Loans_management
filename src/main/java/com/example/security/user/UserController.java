package com.example.security.user;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class UserController {

	@Autowired
	UserService userService ; 
	
	///index page /// 
	@RequestMapping(method = RequestMethod.GET , value = "/adminstration/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("adminstration/index");
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value ="/adminstration/users/user")
	public ModelAndView getUser(@ModelAttribute User user ) {
		ModelAndView mav = new ModelAndView("adminstration/users/user");
		mav.addObject("user",this.userService.getUserByID(user.getUserID()));
		return mav ;
		}
	
	///all users ///
	@RequestMapping(method = RequestMethod.GET , value = "/adminstration/users/all")
	public ModelAndView getAllUsers() {
		ModelAndView mav = new ModelAndView("adminstration/users/all");
		mav.addObject("userslist",this.userService.getAllUsers());
		return mav ; 
	}
	
	///add new user ///
	@RequestMapping(method = RequestMethod.GET , value="/adminstration/users/adduser")
	public ModelAndView addUserView(User user) {
		ModelAndView mav = new ModelAndView("adminstration/adduser");
		mav.addObject("user",new User());
		return mav; 
		//return an empty user model and view  to add a new user 
	}
	
	@RequestMapping(method = RequestMethod.POST , value="/adminstration/users/adduser")
	public void addNewUser(@ModelAttribute User user,HttpServletResponse response) throws IOException {
		this.userService.addUser(user);
		response.sendRedirect("/adminstration/users/all");
	}
	
	
	///update user ///
	@RequestMapping(method = RequestMethod.GET , value="/adminstration/users/update")
	public ModelAndView updateUserRequest(@ModelAttribute User modelUser ) {
		ModelAndView mav = new ModelAndView("adminstration/users/update");
		Optional<User> user = this.userService.getUserByID(modelUser.getUserID());
		mav.addObject("user",user);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.PUT , value="/adminstration/users/update")
	public void updateUser(@ModelAttribute User user,HttpServletResponse response ) throws IOException {
		this.userService.updateUser(user);
		response.sendRedirect("/adminstration/users/all");
	}
	
	
	///delete User ///
	@Transactional
	@RequestMapping(method = RequestMethod.DELETE , value="/adminstration/users/delete")
	public void deleteUser(@ModelAttribute User user ,HttpServletResponse response) throws IOException {
		this.userService.deleteUser(user);
		response.sendRedirect("/adminstration/users/all");
	}
	
}
