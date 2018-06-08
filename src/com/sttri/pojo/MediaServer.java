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
 * 流媒体服务器
 * @author xiaobai
 *
 */
@Entity
@Table(name = "media_server")
public class MediaServer implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private Company company;
	private String serverName;
	private String realPlayUrl;
	private String recordPlayUrl;
	private String recordWebServiceUrl;
	private String recordDownUrl;
	private String cpuUsed;
	private String memoryUsed;
	private Integer devNum;
	private Integer playNum;
	private String hlsServiceUrl;
	private Integer serviceType;
	private String transCodeServiceUrl;
	private Integer onLine;
	
	public MediaServer() {
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
	@JoinColumn(name="comId")
	@NotFound(action=NotFoundAction.IGNORE)
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Column(name = "RealPlayUrl", length = 200)
	public String getRealPlayUrl() {
		return realPlayUrl;
	}

	public void setRealPlayUrl(String realPlayUrl) {
		this.realPlayUrl = realPlayUrl;
	}

	@Column(name = "RecordPlayUrl", length = 200)
	public String getRecordPlayUrl() {
		return recordPlayUrl;
	}

	public void setRecordPlayUrl(String recordPlayUrl) {
		this.recordPlayUrl = recordPlayUrl;
	}

	@Column(name = "RecordWebServiceUrl", length = 200)
	public String getRecordWebServiceUrl() {
		return recordWebServiceUrl;
	}

	public void setRecordWebServiceUrl(String recordWebServiceUrl) {
		this.recordWebServiceUrl = recordWebServiceUrl;
	}

	@Column(name = "RecordDownUrl", length = 100)
	public String getRecordDownUrl() {
		return recordDownUrl;
	}

	public void setRecordDownUrl(String recordDownUrl) {
		this.recordDownUrl = recordDownUrl;
	}

	@Column(name = "CpuUsed", length = 10)
	public String getCpuUsed() {
		return this.cpuUsed;
	}

	public void setCpuUsed(String cpuUsed) {
		this.cpuUsed = cpuUsed;
	}

	@Column(name = "MemoryUsed", length = 10)
	public String getMemoryUsed() {
		return this.memoryUsed;
	}

	public void setMemoryUsed(String memoryUsed) {
		this.memoryUsed = memoryUsed;
	}

	@Column(name = "DevNum")
	public Integer getDevNum() {
		return this.devNum;
	}

	public void setDevNum(Integer devNum) {
		this.devNum = devNum;
	}

	@Column(name = "PlayNum")
	public Integer getPlayNum() {
		return this.playNum;
	}

	public void setPlayNum(Integer playNum) {
		this.playNum = playNum;
	}

	@Column(name = "HlsServiceUrl", length = 200)
	public String getHlsServiceUrl() {
		return hlsServiceUrl;
	}

	public void setHlsServiceUrl(String hlsServiceUrl) {
		this.hlsServiceUrl = hlsServiceUrl;
	}
	
	@Column(name = "ServiceType")
	public Integer getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}
	
	@Column(name = "TransCodeServiceUrl", length = 200)
	public String getTransCodeServiceUrl() {
		return transCodeServiceUrl;
	}

	public void setTransCodeServiceUrl(String transCodeServiceUrl) {
		this.transCodeServiceUrl = transCodeServiceUrl;
	}
	
	@Column(name = "OnLine")
	public Integer getOnLine() {
		return this.onLine;
	}

	public void setOnLine(Integer onLine) {
		this.onLine = onLine;
	}
}