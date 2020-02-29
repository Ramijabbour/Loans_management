package dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.ReScheduleLoans;

@Repository
public interface ReScheduleLoansRepository extends JpaRepository<ReScheduleLoans,Integer>{

}
