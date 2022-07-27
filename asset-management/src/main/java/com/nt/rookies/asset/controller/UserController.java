package com.nt.rookies.asset.controller;


import javax.validation.Valid;

import com.nt.rookies.asset.dto.CheckPasswordDTO;
import com.nt.rookies.asset.dto.UpdatePasswordDTO;
import com.nt.rookies.asset.dto.UserDTO;
import com.nt.rookies.asset.service.UserService;
import com.nt.rookies.asset.util.UserFormatUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserFormatUtils userFormatUtils;

	@PostMapping("")
	ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
		UserDTO dto = userService.createUser(user);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@PutMapping("")
	ResponseEntity<UserDTO> editUser(@Valid @RequestBody UserDTO user) {
		UserDTO dto = userService.editUser(user);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PutMapping("/change-password")
	ResponseEntity<?> changePassword(@RequestBody UpdatePasswordDTO passwordDTO){
		boolean result;
		result = userService.updatePassword(passwordDTO);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/get-password")
	ResponseEntity<?> getPassword(@RequestBody CheckPasswordDTO checkPasswordDTO){
		String result;
		result = userService.checkPassword(checkPasswordDTO);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
