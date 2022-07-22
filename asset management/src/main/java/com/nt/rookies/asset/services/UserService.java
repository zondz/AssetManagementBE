package com.nt.rookies.asset.services;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.nt.rookies.asset.dto.UserDTO;
import com.nt.rookies.asset.entities.UserEntity;
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
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserFormatUtils userFormatUtils;

	@Autowired
	private PasswordEncoder encoder;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserDTO createUser(UserDTO user){

		try{
			UserEntity entity = userMapper.convertToEntity(user);
			entity.setIsDeleted(false);
			entity.setFirstLogin(true);
			String username = userFormatUtils.formatUsername(user.getFirstName(), user.getLastName());
			entity.setUsername(username);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
			String password = entity.getUsername() + '@' + entity.getDob().format(formatter);
			entity.setPassword(encoder.encode(password));
			//save
			UserEntity saveUser = userRepository.save(entity);
			return userMapper.convertToDto(saveUser);
		} catch(Exception ex){
			logger.warn(ex.getMessage());
			throw new UserException(UserException.USER_CREATE_DATA_FAIL);
		}
	}
}
