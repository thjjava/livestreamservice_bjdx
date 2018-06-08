package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ∆Û“µ
 * @author xiaobai
 *
 */
@Entity
@Table(name = "company")
public class Company implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String comName;
	private String comAddress;
	private String comLink;
	private String comLinkPhone;
	private Integer comDevNums;
	private Integer comStoreDays;
	private String addTime;
	private String editTime;
	private String license;
	private Integer hlsLiveFlag;
	
	public Company() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ComName", length = 100)
	public String getComName() {
		return this.comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	@Column(name = "ComAddress", length = 100)
	public String getComAddress() {
		return this.comAddress;
	}

	public void setComAddress(String comAddress) {
		this.comAddress = comAddress;
	}

	@Column(name = "ComLink", length = 20)
	public String getComLink() {
		return this.comLink;
	}

	public void setComLink(String comLink) {
		this.comLink = comLink;
	}

	@Column(name = "ComLinkPhone", length = 11)
	public String getComLinkPhone() {
		return this.comLinkPhone;
	}

	public void setComLinkPhone(String comLinkPhone) {
		this.comLinkPhone = comLinkPhone;
	}

	@Column(name = "ComDevNums")
	public Integer getComDevNums() {
		return this.comDevNums;
	}

	public void setComDevNums(Integer comDevNums) {
		this.comDevNums = comDevNums;
	}

	@Column(name = "ComStoreDays")
	public Integer getComStoreDays() {
		return this.comStoreDays;
	}

	public void setComStoreDays(Integer comStoreDays) {
		this.comStoreDays = comStoreDays;
	}

	@Column(name = "AddTime", length = 30)
	public String getAddTime() {
		return this.addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	@Column(name = "EditTime", length = 30)
	public String getEditTime() {
		return this.editTime;
	}

	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	
	@Column(name = "HlsLiveFlag")
	public Integer getHlsLiveFlag() {
		return this.hlsLiveFlag;
	}

	public void setHlsLiveFlag(Integer hlsLiveFlag) {
		this.hlsLiveFlag = hlsLiveFlag;
	}
	
	@Column(name = "License", length = 200)
	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

}