package com.nt.rookies.asset.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nt.rookies.asset.dto.UserDTO;
import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.enums.EStatus;
import com.nt.rookies.asset.enums.EType;
import com.nt.rookies.asset.exception.UserException;
import com.nt.rookies.asset.mapper.UserMapper;
import com.nt.rookies.asset.repository.UserRepository;
import com.nt.rookies.asset.utils.UserFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserFormatUtils userFormatUtils;

	@Autowired
	private PasswordEncoder encoder;

	public UserDTO createUser(UserDTO user) {
		String role = user.getType();
		if (!role.equalsIgnoreCase(EType.ADMIN.name()) && !role.equalsIgnoreCase(EType.STAFF.name())) {
			throw new UserException(UserException.USER_TYPE_NOT_FOUND);
		}
		user.setType(role.toUpperCase());
		UserEntity entity = userMapper.convertToEntity(user);

		entity.setIsDeleted(false);
		entity.setFirstLogin(true);
		entity.setStatus(EStatus.ACTIVE.name());
		entity.setLocation("Hanoi");
		String username = userFormatUtils.formatUsername(user.getFirstName(), user.getLastName());
		entity.setUsername(username);
		int userAge = userFormatUtils.userAge(entity.getDob());
		if (userAge < 18) {
			throw new UserException(UserException.USER_DOB_INVALID);
		}
		if (!userFormatUtils.compareDate(entity.getDob(), entity.getJoinedDate())) {
			throw new UserException(UserException.USER_JOINED_DATE_EARLIER);
		}
		if (userFormatUtils.isWeekend(entity.getJoinedDate())) {
			throw new UserException(UserException.USER_JOINED_DATE_IS_WEEKEND);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String password = entity.getUsername() + "@" + entity.getDob().format(formatter);
		entity.setPassword(encoder.encode(password));
		entity.setCreatedDate(LocalDateTime.now());
		entity.setLastUpdatedDate(LocalDateTime.now());
		//save
		try {
			UserEntity saveUser = userRepository.save(entity);
			return userMapper.convertToDto(saveUser);
		}
		catch (Exception ex) {
			throw new UserException(UserException.USER_CREATE_DATA_FAIL);
		}

	}

	public UserDTO editUser(UserDTO user) {
		return user;
	}
}
