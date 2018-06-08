package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 设备录像文件
 * @author xiaobai
 *
 */
@Entity
@Table(name = "dev_record_file")
public class DevRecordFile implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String comId;
	private String mediaServerId;
	private String drId;
	private String recordName;
	private String recordStartTime;
	private String recordEndTime;
	private Integer recordSize;
	
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

	@Column(name = "DrId", length = 50)
	public String getDrId() {
		return this.drId;
	}

	public void setDrId(String drId) {
		this.drId = drId;
	}

	@Column(name = "RecordName", length = 200)
	public String getRecordName() {
		return this.recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
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

	@Column(name = "RecordSize")
	public Integer getRecordSize() {
		return this.recordSize;
	}

	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}

}