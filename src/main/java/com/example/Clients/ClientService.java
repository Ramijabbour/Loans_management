package com.example.Clients;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Clients.ClientsRepository;
import com.example.Clients.Clients;

@Service
public class ClientService {

	@Autowired
	private ClientsRepository clientRepository;
	
	
	public List<Clients> GetAllClients() {
		List<Clients> clients=clientRepository.findAll();
		return clients;
	}
	
	public Clients GetClientById(int id)
	{
		List<Clients> allclient=clientRepository.findAll();
		if(allclient.isEmpty())
		{
			System.out.println("empty ClinetsList ");
			return null ;
		}
		for (Clients client : allclient)
		{
			if(client.getClientID()==id)
				return client;
		}
		System.out.println("requested client not found ");
		return null;
		
	}
	
	public Optional <Clients> GetClient(int id)
	{
		return clientRepository.findById(id);
	}
	
	
	public void addClient(Clients client)
	{
		clientRepository.save(client);
	}


	public void deleteClient(int id) {
		clientRepository.deleteById(id);
	}


	public void updateClient(Clients client) {
		try {
			if(clientRepository.findById(client.getClientID()) != null) {
					clientRepository.save(client); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at client Service / Update client -- call for null client ");
		}
	}

}
