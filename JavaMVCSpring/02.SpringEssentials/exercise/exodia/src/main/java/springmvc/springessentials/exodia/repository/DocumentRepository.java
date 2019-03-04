package springmvc.springessentials.exodia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springmvc.springessentials.exodia.domain.entities.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document,String> {
	
	
}
