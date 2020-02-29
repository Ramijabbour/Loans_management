package dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions,Integer> {

}
