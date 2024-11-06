package com.cyberoblivion.peakflow.data;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
        // Custom query method to find a user by username
        Optional<UserAccount> findByUsername(String username);
        Optional<UserAccount> findByEmail(String email);
        Optional<UserAccount> findByUsernameOrEmail(String username, String email);
}
