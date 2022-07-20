package com.nt.rookies.asset.repository;

import java.util.Optional;

import com.nt.rookies.asset.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByUsername(String username);
}
