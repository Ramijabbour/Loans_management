package dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Banks;

@Repository
public interface BanksRepository extends JpaRepository<Banks,Integer>{

}
