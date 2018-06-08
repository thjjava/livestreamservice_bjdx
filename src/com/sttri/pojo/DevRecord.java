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
 * Â¼Ïñ
 * @author xiaobai
 *
 */
@Entity
@Table(name = "dev_record")
public class DevRecord implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String comId;
	private String mediaServerId;
	//private String devId;
	private TblDev dev;
	private Integer recordSource;
	private String recordStartTime;
	private String recordEndTime;
	private Integer recordStatus;
	
	public DevRecord() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ComId", length = 50)
	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	@Column(name = "MediaServerId", length = 50)
	public String getMediaServerId() {
		return mediaServerId;
	}
	
	public void setMediaServerId(String mediaServerId) {
		this.mediaServerId = mediaServerId;
	}

	@ManyToOne
	@JoinColumn(name="devId")
	@NotFound(action=NotFoundAction.IGNORE)
	public TblDev getDev() {
		return dev;
	}

	public void setDev(TblDev dev) {
		this.dev = dev;
	}

	@Column(name = "RecordSource")
	public Integer getRecordSource() {
		return this.recordSource;
	}

	public void setRecordSource(Integer recordSource) {
		this.recordSource = recordSource;
	}

	@Column(name = "RecordStartTime", length = 30)
	public String getRecordStartTime() {
		return this.recordStartTime;
	}

	public void setRecordStartTime(String recordStartTime) {
		this.recordStartTime = recordStartTime;
	}

	@Column(name = "RecordEndTime", length = 30)
	public String getRecordEndTime() {
		return this.recordEndTime;
	}

	public void setRecordEndTime(String recordEndTime) {
		this.recordEndTime = recordEndTime;
	}

	@Column(name = "RecordStatus")
	public Integer getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}

}