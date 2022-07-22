package com.nt.rookies.asset.repository;

import java.util.Optional;

import com.nt.rookies.asset.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, String> {

	Optional<UserEntity> findByUsername(String username);

	boolean existsByUsername(String username);

	@Query(value = "select username\n" +
			" from user\n" +
			" where username like :username% \n"+
			" order by staff_code DESC\n" +
			" LIMIT 1",
			nativeQuery = true)
	String findLastUsername(@Param("username") String username);
}
