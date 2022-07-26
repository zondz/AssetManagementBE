package com.nt.rookies.asset.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "assignment")
@Getter
@Setter
public class AssignmentEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "assign_to")
	private UserEntity assignTo;

	@ManyToOne
	@JoinColumn(name = "assign_by")
	private UserEntity assignBy;

	@Column(name = "asset_code", nullable = false)
	private Integer assetCode;

	@Column(name = "assigned_Date", nullable = false)
	private LocalDateTime assignDate;

	@Column(name = "note", length = 4000)
	private String note;

	@Column(name = "state")
	private String state;

	@ManyToOne
	@JoinColumn(name = "assetcode", nullable = false)
	private AssetEntity asset;

	@OneToMany(mappedBy = "assignment")
	private List<ReturnRequestEntity> requests;
}
