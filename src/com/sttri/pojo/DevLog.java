package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * 用户手机硬件信息
 */
@Entity
@Table(name = "dev_log")
public class DevLog implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private TblDev dev;
	private MediaServer mediaServer;
	private String clientIP;
	private String operatorName;
	private String operatorCode;
	private Integer logType;
	private String logDesc;
	private String addTime;
	
	public DevLog() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="DevId")
	@NotFound(action=NotFoundAction.IGNORE)
	public TblDev getDev() {
		return dev;
	}

	public void setDev(TblDev dev) {
		this.dev = dev;
	}
	
	@ManyToOne
	@JoinColumn(name="ServerId")
	@NotFound(action=NotFoundAction.IGNORE)
	public MediaServer getMediaServer() {
		return mediaServer;
	}

	public void setMediaServer(MediaServer mediaServer) {
		this.mediaServer = mediaServer;
	}
	
	@Column(name = "ClientIP", length = 20)
	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	
	@Column(name = "OperatorName", length = 50)
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	@Column(name = "OperatorCode", length = 20)
	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
	@Column(name = "LogType")
	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	
	@Column(name = "LogDesc", length = 255)
	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	
	@Column(name = "AddTime", length = 30)
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}