package com.nt.rookies.asset.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.nt.rookies.asset.dto.UpdatePasswordDTO;
import com.nt.rookies.asset.dto.UserDTO;
import com.nt.rookies.asset.entity.UserEntity;
import com.nt.rookies.asset.exception.UserException;
import com.nt.rookies.asset.mapper.UserMapper;
import com.nt.rookies.asset.repository.UserRepository;
import com.nt.rookies.asset.util.UserFormatUtils;
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
		if (!role.equalsIgnoreCase(UserEntity.EType.ADMIN.name()) && !role.equalsIgnoreCase(UserEntity.EType.STAFF.name())) {
			throw new UserException(UserException.USER_TYPE_NOT_FOUND);
		}
		user.setType(role.toUpperCase());
		UserEntity entity = userMapper.convertToEntity(user);
		if(userFormatUtils.isFirstNameContainWhiteSpace(entity.getFirstName())){
			throw new UserException(UserException.FIRST_NAME_CONTAINS_WHITESPACE);
		}
		entity.setFirstName(userFormatUtils.formatName(entity.getFirstName()));
		entity.setLastName(userFormatUtils.formatName(entity.getLastName()));
		entity.setIsDeleted(false);
		entity.setFirstLogin(true);
		entity.setStatus(UserEntity.EStatus.ACTIVE.name());
		entity.setLocation("Hanoi");
		String username = getGeneratedUsername(entity.getFirstName(), entity.getLastName());
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

	public String getGeneratedUsername(String fName, String lName) {
		String userName = fName.trim().toLowerCase();
		String afterStr = "";
		for (String s : lName.trim().split(" ")) {
			afterStr += s.charAt(0);
		}
		userName += afterStr;
		String lastIndex = "";
		if (userRepository.findLastUsername(userName) != null) {
			String lastUsername = userRepository.findLastUsername(userName);
			char[] chars = lastUsername.toCharArray();
			for (char c : chars) {
				if (Character.isDigit(c)) {
					String lastNumb = String.valueOf(c);
					lastIndex += Integer.parseInt(lastNumb) + 1;
				}
				if (c == chars[chars.length - 1] && !Character.isDigit(c)) {
					lastIndex += 1;
				}
			}
		}
		return userName.toLowerCase() + lastIndex;
	}

	public UserDTO editUser(UserDTO user) {
		UserEntity oldUser = userRepository.findById(user.getStaffCode())
				.orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		oldUser.setDob(LocalDate.parse(user.getDob(), formatter));
		oldUser.setGender(user.getGender());
		oldUser.setJoinedDate(LocalDate.parse(user.getJoinedDate(), formatter));
		String role = user.getType();
		if (!role.equalsIgnoreCase(UserEntity.EType.ADMIN.name()) && !role.equalsIgnoreCase(UserEntity.EType.STAFF.name())) {
			throw new UserException(UserException.USER_TYPE_NOT_FOUND);
		}
		oldUser.setType(user.getType().toUpperCase());
		try {
			UserEntity newUser = userRepository.save(oldUser);
			return userMapper.convertToDto(newUser);
		} catch (Exception ex){
			throw new UserException(UserException.USER_UPDATE_FAIL);
		}

	}

	public boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {
		boolean result = false;
		try {
			Optional<UserEntity> existedUser = userRepository.findById(updatePasswordDTO.getCode());
			if (!existedUser.isPresent()) {
				logger.info("User {} not found", updatePasswordDTO.getCode());
				throw new UserException(UserException.USER_NOT_FOUND);
			}
			UserEntity user = existedUser.get();
			boolean check = this.encoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword());
			if (check) {
				user.setPassword(encoder.encode(updatePasswordDTO.getNewPassword()));
				userRepository.save(user);
				result = true;
			} else {
				throw new UserException(UserException.ERR_WRONG_OLD_PASSWORD);
			}
		} catch (UserException ex) {
			throw new UserException(ex.getCodeResponse());
		} catch (Exception e) {
			logger.info("Fail to update user {}", updatePasswordDTO.getCode());
			throw new UserException(UserException.ERR_UPDATE_USER_FAIL);
		}

		return result;
	}
}
