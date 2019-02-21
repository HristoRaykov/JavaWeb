package org.softuni.exam.repository;

import org.softuni.exam.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, String> {

    Optional<User> findByUsername(String username);
    
}
