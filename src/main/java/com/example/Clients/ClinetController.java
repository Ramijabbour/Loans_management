package com.example.Clients;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.Clients.Clients;

@RestController
public class ClinetController {

	@Autowired
	private ClientService clientservice;
	

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
	
	
	@RequestMapping(method = RequestMethod.PUT , value="/Clients")
	public void updateClient(@RequestBody Clients client)
	{
		clientservice.updateClient(client);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE , value="/Clients/{id}")
	public void deleteClient(@PathVariable int id)
	{
		clientservice.deleteClient(id);
		
	}
	
	//add new Client -------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Clients/addClient")
	public ModelAndView addClientRequest() {
		ModelAndView mav = new ModelAndView("Clients/AddClients");
		mav.addObject("client",new Clients());
		return mav; 
	}
	 
	
	@RequestMapping(method = RequestMethod.POST , value="/Clients/addClient")
	public void addNewClient(@ModelAttribute Clients client,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Clients/addClient ");
		System.out.println(client.getClientType()+"----------------------");
		if(client.getIdentity_number()==null)
			client.setIdentity_number(""); 
		clientservice.addClient(client);
		response.sendRedirect("/Clients/all");
	}
	// -----------------------------------------------------------------------   
	  
	
	//get All Clients ------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Clients/all")
		public ModelAndView ShowAllBank() {
			ModelAndView mav = new ModelAndView("Clients/AllClients");
			List<Clients> allclients=clientservice.GetAllClients();
			mav.addObject("allclients",allclients);
			return mav; 
		}
   // -------------------------------------------------------------------
	
	
	
	
	
	
}
