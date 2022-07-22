package com.nt.rookies.asset.entities;


import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "asset")
@Getter
@Setter
public class AssetEntity extends BaseEntity{
	@Id
	@Column(name = "code")
	@GeneratedValue(generator = "asset-code-generator")
	@GenericGenerator(name = "asset-code-generator", strategy = "com.nt.rookies.asset.utils.AssetCodeGenerator")
	private String assetCode;

	@Column(name = "name")
	private String assetName;

	@Column(name = "state")
	private Short state;

	@Column(name = "installdate")
	private LocalDateTime installDate;

	@Column(name = "location")
	private String location;

	@Column(name = "specification")
	private String specification;

	@Column(name = "isdeleted")
	private Boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity category;

	@OneToMany(mappedBy = "asset" ,fetch = FetchType.LAZY)
	private List<AssignmentEntity> assignments;
}
