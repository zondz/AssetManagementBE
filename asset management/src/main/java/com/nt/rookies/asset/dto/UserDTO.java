package com.nt.rookies.asset.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Data
@Getter
@Setter
@ToString
public class UserDTO {
	private String status;

	@NotBlank(message = "First name can not be empty")
	@Length(min = 2, max = 10, message = "First name is not valid! Please input first name with length from 2 to 10 letters")
	@Pattern(regexp = "[a-zA-Z]+", message = "First name must not contain numbers, whitespace and special characters")
	private String firstName;

	@NotBlank(message = "Last name can not be empty")
	@Length(min = 2, max = 20, message = "Last name is not valid! Please input last name with length from 2 to 20 letters")
	@Pattern(regexp = "[a-zA-Z\\s]+", message = "Last name must not contain numbers and special characters")
	private String lastName;

	private String username;

	@NotBlank(message = "Date of birth can not be empty")
	private String dob;

	@NotBlank(message = "Joined date can not be empty")
	private String joinedDate;

	private String gender;

	@NotBlank(message = "Type can not be empty")
	private String type;

	private String location;

	private boolean isDeleted;

	private boolean isFirstLogin;
}
