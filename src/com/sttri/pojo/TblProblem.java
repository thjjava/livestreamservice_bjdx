package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户手机硬件信息
 */
@Entity
@Table(name = "tbl_problem")
public class TblProblem implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String devNo;
	private String mobileModel;
	private String systemOS;
	private Integer netType;
	private String content;
	private String addTime;
	
	public TblProblem() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "DevNo", length = 50)
	public String getDevNo() {
		return devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}
	
	@Column(name = "MobileModel", length = 50)
	public String getMobileModel() {
		return mobileModel;
	}

	public void setMobileModel(String mobileModel) {
		this.mobileModel = mobileModel;
	}

	@Column(name = "SystemOS", length = 20)
	public String getSystemOS() {
		return systemOS;
	}

	public void setSystemOS(String systemOS) {
		this.systemOS = systemOS;
	}
	
	@Column(name = "NetType")
	public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}
	
	@Column(name = "Content", length = 500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "AddTime", length = 30)
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}