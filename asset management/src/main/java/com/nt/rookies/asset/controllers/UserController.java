package com.nt.rookies.asset.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.services.UserService;
import com.nt.rookies.asset.utils.UserFormatUtils;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  UserFormatUtils userFormatUtils;

  @PostMapping("")
  ResponseEntity createUser(@RequestBody UserEntity user) {
    //userService.createUser(user);
    String username = userFormatUtils.formatUsername(user.getFirstName(), user.getLastName());
    return new ResponseEntity<>(username, HttpStatus.OK);
  }

}
