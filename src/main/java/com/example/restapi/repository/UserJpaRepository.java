package com.example.restapi.repository;

import java.util.Optional;

import com.example.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid (String emalil);

    void deleteById(int msrl);
    
}
