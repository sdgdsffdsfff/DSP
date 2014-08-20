package com.aben.iuc.entity.main;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.aben.iuc.entity.IdEntity;
import com.google.common.base.Objects;

@Entity
@Table(name = "security_sys_sort")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "com.gionee.iuc.entity.main")
public class SysSort extends IdEntity {
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sortid")
	private Sort sort;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sysid")
	private Module module;

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public String toString() {
		return Objects.toStringHelper(this)
				.addValue(sort)
				 .toString();
	}

}
