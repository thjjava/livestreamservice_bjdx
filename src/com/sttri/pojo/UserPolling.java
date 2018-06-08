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
 * ÓÃ»§ÂÖÑ¯²ßÂÔ
 */
@Entity
@Table(name = "user_polling")
public class UserPolling implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private TblUser user;
	private String pollingName;
	private int timeLen;
	private String devList;
	private String addTime;
	
	public UserPolling() {
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
	@JoinColumn(name="userID")
	@NotFound(action=NotFoundAction.IGNORE)
	public TblUser getUser() {
		return user;
	}

	public void setUser(TblUser user) {
		this.user = user;
	}
	
	@Column(name = "PollingName", length = 100)
	public String getPollingName() {
		return pollingName;
	}

	public void setPollingName(String pollingName) {
		this.pollingName = pollingName;
	}

	@Column(name = "TimeLen")
	public int getTimeLen() {
		return timeLen;
	}

	public void setTimeLen(int timeLen) {
		this.timeLen = timeLen;
	}

	@Column(name = "DevList", length = 4096)
	public String getDevList() {
		return devList;
	}

	public void setDevList(String devList) {
		this.devList = devList;
	}

	@Column(name = "AddTime", length = 30)
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}