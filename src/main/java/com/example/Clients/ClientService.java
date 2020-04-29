package com.example.Clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Clients.ClientsRepository;
import com.example.SiteConfiguration;
import com.example.Clients.Clients;

@Service
public class ClientService {

	@Autowired
	private ClientsRepository clientRepository;
	
	
	public List<Clients> GetAllClients(int PageNumber) {
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<Clients> pagedResult = this.clientRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Clients>();
        }
	}
	
	public List<Clients> GetAllClientsNoPage() {
		
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
			if(client.getId()==id)
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
			if(clientRepository.findById(client.getId()) != null) {
					clientRepository.save(client); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at client Service / Update client -- call for null client ");
		}
	}

}
