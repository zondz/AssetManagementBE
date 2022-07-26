package com.nt.rookies.asset.mapper;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.nt.rookies.asset.dto.UserDTO;
import com.nt.rookies.asset.entities.UserEntity;
import com.nt.rookies.asset.exception.UserException;
import com.nt.rookies.asset.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder encoder;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserDTO convertToDto(UserEntity user) {
		try {
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);

			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			userDTO.setDob(user.getDob().format(formatterDate));
			userDTO.setJoinedDate(user.getJoinedDate().format(formatterTime));

			return userDTO;
		}catch (Exception ex){
			logger.warn(ex.getMessage());
			throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
		}

	}

	public UserEntity convertToEntity(UserDTO userDTO) {
		try {
			UserEntity user = modelMapper.map(userDTO , UserEntity.class);

			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			user.setDob(LocalDate.parse(userDTO.getDob(), formatterDate));
			user.setJoinedDate(LocalDateTime.parse(userDTO.getJoinedDate(), formatterTime));
			return user;
		}catch (Exception ex){
			logger.warn(ex.getMessage());
			throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
		}
	}

	public List<UserDTO> toListDto(List<UserEntity> listEntity) {
		List<UserDTO> listDto = new ArrayList<>();

		listEntity.forEach(e->{
			listDto.add(this.convertToDto(e));
		});
		return listDto;
	}

}
