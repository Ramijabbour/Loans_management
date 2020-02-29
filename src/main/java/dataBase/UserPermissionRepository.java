package dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.UserPermission;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermission,Integer>{

}
