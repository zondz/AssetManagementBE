package com.nt.rookies.asset.controller.validation;

import com.nt.rookies.asset.entity.UserEntity;
import com.nt.rookies.asset.exception.UserException;
import com.nt.rookies.asset.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserValidator {

	private final UserService userService;

	@Autowired
	public UserValidator(UserService userService) {
		this.userService = userService;
	}

	public UserEntity validateAuthRequest(UserEntity authRequest) {
		return Validator.data(authRequest)
				.validate(UserEntity::getUsername, StringUtils::isEmpty, () -> new UserException(UserException.USER_SAVE_USERNAME_NOT_BLANK))
				.validate(UserEntity::getPassword, StringUtils::isEmpty, () -> new UserException(UserException.USER_SAVE_PASSWORD_NOT_BLANK))
				.getData();
	}
}
