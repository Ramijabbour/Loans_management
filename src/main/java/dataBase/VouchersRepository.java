package dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Vouchers;

@Repository
public interface VouchersRepository extends JpaRepository<Vouchers,Integer>{

}
