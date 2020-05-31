package com.example.Clients;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.Clients.Clients;
import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;


@RestController
public class ClinetController {
	
	@Autowired
	private ClientService clientservice;
	
	
	public ClinetController() {
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			if(!methodsNames.contains(method.getName()))
				{
					methodsNames.add(method.getName());
				}
		}
		methodsNames.add(this.getClass().getSimpleName());
		MasterService.addPermissionsToPermissionService(methodsNames);
	}
	
	@RequestMapping("/Clients/{id}")
	public Clients GetClient(@PathVariable int id)
	{
		Optional<Clients> client=clientservice.GetClient(id);
		if(client.isPresent())
		{
			return client.get();
		}
		else 
			throw new RuntimeException("Clien"+ id + "is not Exist " );
	}
	
	
	//add new Client -------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Clients/addClient")
	public ModelAndView addNewClient() {
		ModelAndView mav = new ModelAndView("Clients/AddClients");
		mav.addObject("client",new Clients());
		return mav; 
	}
	 
	
	@RequestMapping(method = RequestMethod.POST , value="/Clients/addClient")
	public void addNewClient(@ModelAttribute Clients client,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Clients/addClient ");
		clientservice.addClient(client);
		response.sendRedirect("/Clients/all?index=0");
	}
	// -----------------------------------------------------------------------   
	  
	
	//get All Clients ------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Clients/all")
		public ModelAndView viewAllClients(@Param(value ="index") int index) {
			ModelAndView mav = new ModelAndView("Clients/AllClients");
			List<Clients> allclients=clientservice.GetAllClients(index);
			mav.addObject("allclients",allclients);
			if(allclients.size() > 0 ) {
				SiteConfiguration.addSequesnceVaraibles(mav, index);
			}else {
				SiteConfiguration.addSequesnceVaraibles(mav, -1);
			}
			return mav;  
		}
   // -------------------------------------------------------------------
	
	
		// update client--------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Clients/edit/{id}")
		public ModelAndView UpdateClient(@PathVariable int id) {
			ModelAndView mav = new ModelAndView("Clients/updateClient");
			Clients client=clientservice.GetClientById(id);
			mav.addObject("client",client);
			return mav; 
		} 
		
		
		@RequestMapping(method = RequestMethod.POST , value="/Clients/update/{id}")
		public void UpdateClient(@Valid Clients client,HttpServletResponse response) throws IOException {
			System.out.println("posted to /Clients/update/id ");
			clientservice.updateClient(client);
			response.sendRedirect("/Clients/all");
		}
		

		
		//----------------------------------------------------------------
		
		//delete Bank ----------------------------------------------------------  
		@RequestMapping(method = RequestMethod.GET, value="/Clients/delete/{id}")
		public void deleteClient(@PathVariable int id,HttpServletResponse response) throws IOException
		{
			clientservice.deleteClient(id);
			response.sendRedirect("/Clients/all");
		}
		//----search

	@RequestMapping(method = RequestMethod.POST , value = "/Clients/Search")
	public ModelAndView SearchByClientName(@Param(value ="index") int index,@RequestParam("search") String clientName) {
		ModelAndView mav = new ModelAndView("Clients/searchClients");
		List<Clients> allclients = this.clientservice.SearchbyclientName(index,clientName);
		mav.addObject("allclients",allclients);
		mav.addObject("searchvar",clientName);
		if(allclients.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
		
	@RequestMapping(method = RequestMethod.GET , value = "/Clients/Search/nxtRes/{index}/{searchvar}")
	public ModelAndView searchClientkNext(@PathVariable int index,@PathVariable String searchvar ) {
		ModelAndView mav = new ModelAndView("Clients/searchClients");
		List<Clients> allclients = this.clientservice.SearchbyclientName(index,searchvar);
		mav.addObject("allbank",allclients);
		mav.addObject("searchvar",searchvar);
		if(allclients.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	
	
}
