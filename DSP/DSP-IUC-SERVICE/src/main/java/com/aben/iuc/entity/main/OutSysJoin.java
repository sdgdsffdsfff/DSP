package com.aben.iuc.entity.main;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.aben.iuc.entity.IdEntity;

/**
 * 外部系统接入信息对象
 * @author nijiangli
 *
 */
@Entity
@Table(name="security_outsys_join")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="com.gionee.iuc.entity.main")
public class OutSysJoin  extends IdEntity {
 
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Length(min=1, max=100)
	@Column(nullable=false, length=100)
	private String appkey;
	

	@NotBlank
	@Length(min=1, max=100)
	@Column(nullable=false, length=100)
	private String appid;
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@NotBlank
	@Length(min=1, max=1000)
	@Column(nullable=false, length=1000)
	private String joinip;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	private Date createTime;
	
	@OneToOne
	@JoinColumn(name="sys_moudleid")
	private Module moudle;
	
	public Module getMoudle() {
		return moudle;
	}

	public void setMoudle(Module moudle) {
		this.moudle = moudle;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getJoinip() {
		return joinip;
	}

	public void setJoinip(String joinip) {
		this.joinip = joinip;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



}
