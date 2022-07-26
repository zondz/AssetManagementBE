package com.nt.rookies.asset.controller;


import javax.validation.Valid;

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

//		try {
		result = userService.updatePassword(passwordDTO);
//		} catch (NotFoundException e) {
//			throw new NotFoundException(e.getMessage());
//		} catch (Exception ex) {
//			throw new UserException(UserException.ERR_UPDATE_USER_FAIL);
//		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
