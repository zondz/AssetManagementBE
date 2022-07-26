package com.nt.rookies.asset.mapper;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.nt.rookies.asset.dto.UserDTO;
import com.nt.rookies.asset.entity.UserEntity;
import com.nt.rookies.asset.exception.UserException;
import com.nt.rookies.asset.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder encoder;

	public UserDTO convertToDto(UserEntity user) {
		try {
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);

			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			userDTO.setDob(user.getDob().format(formatterDate));
			userDTO.setJoinedDate(user.getJoinedDate().format(formatterDate));

			return userDTO;
		}
		catch (Exception ex) {
			logger.warn(ex.getMessage());
			throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
		}

	}

	public UserEntity convertToEntity(UserDTO userDTO) {
		try {
			UserEntity user = modelMapper.map(userDTO, UserEntity.class);
//			String status = userDTO.getStatus();
//			if(status.equalsIgnoreCase(EStatus.ACTIVE.name()) && status.equalsIgnoreCase(EStatus.ACTIVE.name())){
//				throw new UserException(UserException.USER_STATUS_NOT_FOUND);
//			}
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			user.setDob(LocalDate.parse(userDTO.getDob(), formatterDate));
			user.setJoinedDate(LocalDate.parse(userDTO.getJoinedDate(), formatterDate));
			user.setFirstName(user.getFirstName().trim());
			user.setLastName(user.getLastName().trim());
			return user;
		}
		catch (Exception ex) {
			logger.warn(ex.getMessage());
			throw new UserException(UserException.ERR_CONVERT_DTO_ENTITY_FAIL);
		}
	}

	public List<UserDTO> toListDto(List<UserEntity> listEntity) {
		List<UserDTO> listDto = new ArrayList<>();

		listEntity.forEach(e -> {
			listDto.add(this.convertToDto(e));
		});
		return listDto;
	}

}
