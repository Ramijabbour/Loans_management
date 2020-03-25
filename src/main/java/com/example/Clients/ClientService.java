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
		clientRepository.save(client);
	}

}
