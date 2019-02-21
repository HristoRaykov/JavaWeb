package org.softuni.sboj.repository;

import org.softuni.sboj.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User, String> {

    Optional<User> findByUsername(String username);
    
}
