package com.nt.rookies.asset.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "assignment")
@Getter
@Setter
public class AssignmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "assign_to", nullable = false)
	private Integer assignTo;

	@Column(name = "assign_by", nullable = false)
	private Integer assignBy;

	@Column(name = "asset_code", nullable = false)
	private Integer assetCode;

	@Column(name = "assigned_Date", nullable = false)
	private LocalDateTime assignDate;

	@Column(name = "note", length = 4000)
	private String note;

	@Column(name = "state")
	private String state;
}
