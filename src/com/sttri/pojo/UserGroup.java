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
 * UserGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_group")
public class UserGroup implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	//private String userId;
	private TblUser user;
	//private String groupId;
	private CompanyGroup group;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="userId")
	@NotFound(action=NotFoundAction.IGNORE)
	public TblUser getUser() {
		return user;
	}

	public void setUser(TblUser user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="groupId")
	@NotFound(action=NotFoundAction.IGNORE)
	public CompanyGroup getGroup() {
		return group;
	}

	public void setGroup(CompanyGroup group) {
		this.group = group;
	}

}