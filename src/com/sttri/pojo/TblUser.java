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
 * ”√ªß
 * @author xiaobai
 *
 */
@Entity
@Table(name = "tbl_user")
public class TblUser implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private Integer accountType;
	//private String comId;
	private Company company;
	private String account;
	private transient String pwd;
	private String addTime;
	private String editTime;
	private String modifyPwdTime;
	private Integer loginTimes;
	private Integer errorLoginTimes;
	private String errorLoginTime;
	
	public TblUser() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "AccountType")
	public Integer getAccountType() {
		return this.accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
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

	@Column(name = "Account", length = 50)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "Pwd", length = 100)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	@Column(name = "LoginTimes")
	public Integer getLoginTimes() {
		return this.loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	
	@Column(name = "ModifyPwdTime", length = 20)
	public String getModifyPwdTime() {
		return this.modifyPwdTime;
	}

	public void setModifyPwdTime(String modifyPwdTime) {
		this.modifyPwdTime = modifyPwdTime;
	}
	
	@Column(name = "ErrorLoginTimes")
	public Integer getErrorLoginTimes() {
		return this.errorLoginTimes;
	}

	public void setErrorLoginTimes(Integer errorLoginTimes) {
		this.errorLoginTimes = errorLoginTimes;
	}
	
	@Column(name = "ErrorLoginTime", length = 20)
	public String getErrorLoginTime() {
		return this.errorLoginTime;
	}

	public void setErrorLoginTime(String errorLoginTime) {
		this.errorLoginTime = errorLoginTime;
	}
}