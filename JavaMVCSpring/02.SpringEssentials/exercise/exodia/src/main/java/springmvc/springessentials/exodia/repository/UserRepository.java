package springmvc.springessentials.exodia.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springmvc.springessentials.exodia.domain.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
	
	Optional<User> findByUsername(String username);
	
}
