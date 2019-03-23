package thymeleafexercise.residentevil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thymeleafexercise.residentevil.domain.entities.Virus;

@Repository
public interface VirusRepository extends JpaRepository<Virus,String> {



}
