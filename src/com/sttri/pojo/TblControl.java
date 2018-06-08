package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TblControl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_control", catalog = "livestream")
public class TblControl implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private Integer sourceType;
	private Integer conType;
	private Integer upgradeStatus;
	private String conName;
	private String conVer;
	private String conPath;
	private String conPushTime;
	private String addTime;
	private String editTime;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SourceType")
	public Integer getSourceType() {
		return this.sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	@Column(name = "ConType")
	public Integer getConType() {
		return this.conType;
	}

	public void setConType(Integer conType) {
		this.conType = conType;
	}

	@Column(name = "UpgradeStatus")
	public Integer getUpgradeStatus() {
		return this.upgradeStatus;
	}

	public void setUpgradeStatus(Integer upgradeStatus) {
		this.upgradeStatus = upgradeStatus;
	}

	@Column(name = "ConName", length = 30)
	public String getConName() {
		return this.conName;
	}

	public void setConName(String conName) {
		this.conName = conName;
	}

	@Column(name = "ConVer", length = 20)
	public String getConVer() {
		return this.conVer;
	}

	public void setConVer(String conVer) {
		this.conVer = conVer;
	}

	@Column(name = "ConPath", length = 200)
	public String getConPath() {
		return this.conPath;
	}

	public void setConPath(String conPath) {
		this.conPath = conPath;
	}

	@Column(name = "ConPushTime", length = 30)
	public String getConPushTime() {
		return this.conPushTime;
	}

	public void setConPushTime(String conPushTime) {
		this.conPushTime = conPushTime;
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

}