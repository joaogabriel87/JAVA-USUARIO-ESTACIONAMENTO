package com.user.users_parking.Repository;

import com.user.users_parking.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<UserDetails> findUserByUsername(String username);
    Optional<Users> findUserByEmail(String email);
}
