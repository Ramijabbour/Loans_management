package com.example.Clients;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.Clients.Clients;

@RestController
public class ClinetController {

	@Autowired
	private ClientService clientsercice;
	
	
	@RequestMapping("/Clients")
	public List<Clients> GetAllClient ()
	{
		return clientsercice.GetAllClients();
	}
	
	@RequestMapping("/Clients/{id}")
	public Clients GetClient(@PathVariable int id)
	{
		Optional<Clients> client=clientsercice.GetClient(id);
		if(client.isPresent())
		{
			return client.get();
		}
		else 
			throw new RuntimeException("Clien"+ id + "is not Exist " );
	}
	
	@RequestMapping(method = RequestMethod.POST , value="/addClients")
	public void addClient(@RequestBody Clients client)
	{
		clientsercice.addClient(client);
	}
	
	@RequestMapping(method = RequestMethod.PUT , value="/Clients")
	public void updateClient(@RequestBody Clients client)
	{
		clientsercice.updateClient(client);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE , value="/Clients/{id}")
	public void deleteClient(@PathVariable int id)
	{
		clientsercice.deleteClient(id);
		
	}
}
