package com.nt.rookies.asset.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "staff_code")
	private String staffCode;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "type")
	private String type;

	@Column(name = "gender")
	private String gender;

	@Column(name = "joined_date", nullable = false)
	private LocalDateTime joinedDate;

	@Column(name = "dob")
	private LocalDate dob;

	@Column(name = "fname")
	private String firstName;

	@Column(name = "lname")
	private String lastName;

	@Column(name = "location")
	private String location;

	@Column(name = "status")
	private String status;

}
