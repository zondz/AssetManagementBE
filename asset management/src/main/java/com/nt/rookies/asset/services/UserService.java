package com.nt.rookies.asset.services;

import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	public UserEntity createUser(UserEntity user){
		return userRepository.save(user);
	}
}
