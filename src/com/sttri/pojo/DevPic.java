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
 * ×¥ÅÄÍ¼Æ¬
 * @author xiaobai
 *
 */
@Entity
@Table(name = "dev_pic")
public class DevPic implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	//private String devId;
	private TblDev dev;
	private String picPath;
	private String grapTime;
	
	public DevPic() {
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
	@JoinColumn(name="devId")
	@NotFound(action=NotFoundAction.IGNORE)
	public TblDev getDev() {
		return dev;
	}

	public void setDev(TblDev dev) {
		this.dev = dev;
	}

	@Column(name = "PicPath", length = 200)
	public String getPicPath() {
		return this.picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Column(name = "GrapTime", length = 30)
	public String getGrapTime() {
		return this.grapTime;
	}

	public void setGrapTime(String grapTime) {
		this.grapTime = grapTime;
	}

}