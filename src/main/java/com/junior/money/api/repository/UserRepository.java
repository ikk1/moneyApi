package com.junior.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.junior.money.api.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

    UserDetails findByLogin(String login);

}
