package com.nt.rookies.asset.entities;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "return_request")
@Getter
@Setter
public class ReturnRequestEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_id")
	private Long requestId;

	@Column(name = "request_by")
	private String requestBy;

	@Column(name = "accept_by")
	private String acceptBy;

	@Column(name = "return_date")
	private LocalDateTime returnDate;

	@Column(name = "state")
	private String state;

	@ManyToOne
	@JoinColumn(name = "assignment_id", nullable = false)
	private AssignmentEntity assignment;

}
