package com.nt.rookies.asset.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.repository.UserRepository;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;

  public UserEntity createUser(UserEntity user) {
    return userRepository.save(user);
  }

  public UserEntity findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  public UserEntity updatePasswordFirstTime(UserEntity updateEntity) {

    return userRepository.save(updateEntity);
  }

}
