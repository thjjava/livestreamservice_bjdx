package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


@Entity
@Table(name = "role_menus")
public class RoleMenus implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private TblMenus menus;
	private TblRole role;
	
	public RoleMenus() {
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
	@JoinColumn(name="MenuId")
	@NotFound(action=NotFoundAction.IGNORE)
	public TblMenus getMenus() {
		return menus;
	}

	public void setMenus(TblMenus menus) {
		this.menus = menus;
	}
	
	@ManyToOne
	@JoinColumn(name="RoleId")
	@NotFound(action=NotFoundAction.IGNORE)
	public TblRole getRole() {
		return role;
	}

	public void setRole(TblRole role) {
		this.role = role;
	}
	

}