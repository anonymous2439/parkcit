package com.cit.parkcit.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.cit.parkcit.model.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}