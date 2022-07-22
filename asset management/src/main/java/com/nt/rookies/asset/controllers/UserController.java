package com.nt.rookies.asset.controllers;


import javax.validation.Valid;

import com.nt.rookies.asset.dto.UserDTO;
import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.services.UserService;
import com.nt.rookies.asset.utils.UserFormatUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserFormatUtils userFormatUtils;

	@PostMapping("")
	ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user){
		UserDTO dto = userService.createUser(user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
}
