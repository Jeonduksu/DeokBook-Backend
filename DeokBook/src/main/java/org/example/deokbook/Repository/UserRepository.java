package org.example.deokbook.Repository;

import jakarta.validation.constraints.NotBlank;
import org.example.deokbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.loans l WHERE u.id = :id")
    Optional<User> findByWithLoans(@Param("id") Long id);

    List<User> findByNameContaining(String name);
}
