package com.embarkx.embarkxquiz.repositories.user;

import com.embarkx.embarkxquiz.enums.UserRole;
import com.embarkx.embarkxquiz.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findByRole(UserRole role);
    List<User> findByIsActive(Boolean isActive);
    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);
}
