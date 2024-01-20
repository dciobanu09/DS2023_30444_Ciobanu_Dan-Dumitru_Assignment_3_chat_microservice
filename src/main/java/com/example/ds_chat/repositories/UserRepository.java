package com.example.ds_chat.repositories;

import com.example.ds_chat.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
