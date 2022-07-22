package com.nt.rookies.asset.exception;

import lombok.Getter;
import lombok.Setter;

import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserException extends RuntimeException{

	private CodeResponse codeResponse;

	public static final CodeResponse SUCCESS =
			new CodeResponse("SUCCESS", "SUCCESS", HttpStatus.OK);
	public static final CodeResponse USER_NOT_FOUND
			= new CodeResponse("USER-01", "User not found", HttpStatus.NOT_FOUND);
	public static final CodeResponse JWT_EXPIRED
			= new CodeResponse("JWT-01", "Jwt expired", HttpStatus.UNAUTHORIZED);
	public static final CodeResponse JWT_INVALID
			= new CodeResponse("JWT-02", "Jwt invalid", HttpStatus.UNAUTHORIZED);
	public static final CodeResponse USER_LOGIN_FAIL
			= new CodeResponse("USER-AUTH-01", "UserName or Password is wrong", HttpStatus.BAD_REQUEST);
	public static final CodeResponse USER_SAVE_PASSWORD_NOT_BLANK
			= new CodeResponse("USER-02", "Password not blank", HttpStatus.BAD_REQUEST);
	public static final CodeResponse USER_SAVE_USERNAME_NOT_BLANK
			= new CodeResponse("USER-03", "UserName not blank", HttpStatus.BAD_REQUEST);
	public static final CodeResponse USER_CREATE_DATA_FAIL
			= new CodeResponse("USER-04", "Create User fail", HttpStatus.INTERNAL_SERVER_ERROR);
	public static final CodeResponse ERR_CONVERT_DTO_ENTITY_FAIL
			= new CodeResponse("USER-05", "Convert User fail", HttpStatus.INTERNAL_SERVER_ERROR);
	public static final CodeResponse USER_DOB_INVALID
			= new CodeResponse("USER-06", "User is under 18. Please select a different date"
			, HttpStatus.BAD_REQUEST);
	public static final CodeResponse USER_JOINED_DATE_EARLIER
			= new CodeResponse("USER-07", "Joined date is not later than Date of Birth. Please select a different date"
			, HttpStatus.BAD_REQUEST);
	public static final CodeResponse USER_JOINED_DATE_IS_WEEKEND
			= new CodeResponse("USER-08", "Joined date is Saturday or Sunday. Please select a different date"
			, HttpStatus.BAD_REQUEST);

	public UserException(CodeResponse codeResponse) {
		this.codeResponse = codeResponse;
	}
}
