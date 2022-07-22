package com.nt.rookies.asset.entities;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "category", uniqueConstraints = {
		@UniqueConstraint(columnNames = "category_name"),
		@UniqueConstraint(columnNames = "prefix")
})
@Getter
@Setter
public class CategoryEntity extends BaseEntity{
	@Id
	@Column(name = "id")
	private Integer categoryId;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "prefix")
	private String prefix;

	@OneToMany(mappedBy = "category")
	private List<AssetEntity> assets;
}
