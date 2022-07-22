package com.nt.rookies.asset.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


@Entity
@Table(name = "user", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username")
})
@Getter
@Setter
public class UserEntity extends BaseEntity {

	@Id
	@Column(name = "staff_code")
	@GeneratedValue(generator = "staff-code-generator")
	@GenericGenerator(name = "staff-code-generator",
			parameters = @Parameter(name = "prefix", value = "SD"),
			strategy = "com.nt.rookies.asset.utils.CodeGenerator")
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

	@Column(name = "is_first_login")
	private Boolean firstLogin;

	@OneToMany(mappedBy = "assignTo" , fetch = FetchType.LAZY)
	private List<AssignmentEntity> assignToList;

	@OneToMany(mappedBy = "assignBy" , fetch = FetchType.LAZY)
	private List<AssignmentEntity> assignByList;

}
