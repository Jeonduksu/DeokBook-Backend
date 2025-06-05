package org.example.deokbook.domain.user.repository;

import org.example.deokbook.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
