package com.aben.iuc.entity.main;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.aben.iuc.entity.IdEntity;
import com.google.common.base.Objects;

@Entity
@Table(name="security_sort")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gionee.iuc.entity.main")
public class Sort extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分类名称
	 */
	@NotBlank
	@Length(min=1, max=32)
	@Column(nullable=false, length=32)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(name)
				.toString();
	}
}
