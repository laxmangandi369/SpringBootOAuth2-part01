package willydekeyser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import willydekeyser.entity.SecurityUser;

@Repository
public interface UserRepository extends JpaRepository<SecurityUser, Integer> {

	SecurityUser findByUsername(String username);
}

