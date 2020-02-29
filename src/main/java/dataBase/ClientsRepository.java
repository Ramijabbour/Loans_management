package dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Clients;

@Repository
public interface ClientsRepository extends JpaRepository<Clients,Integer>{

}
