package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * IPµÿ÷∑ø‚
 */
@Entity
@Table(name = "tbl_ip")
public class TblIP implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Long startnum;
	private Long endnum;
	private String ipstart;
	private String ipend;
	private String area;
	private String country;
	private String province;
	private String city;
	private String address;
	private String isp;
	

	public TblIP() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "startnum")
	public Long getStartnum() {
		return startnum;
	}

	public void setStartnum(Long startnum) {
		this.startnum = startnum;
	}
	
	@Column(name = "endnum")
	public Long getEndnum() {
		return endnum;
	}

	public void setEndnum(Long endnum) {
		this.endnum = endnum;
	}
	
	@Column(name = "ipstart", length = 255)
	public String getIpstart() {
		return ipstart;
	}

	public void setIpstart(String ipstart) {
		this.ipstart = ipstart;
	}

	@Column(name = "ipend", length = 255)
	public String getIpend() {
		return ipend;
	}

	public void setIpend(String ipend) {
		this.ipend = ipend;
	}
	
	
	@Column(name = "area", length = 255)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name = "country", length = 255)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "province", length = 255)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "city", length = 255)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "address", length = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "isp", length = 255)
	public String getIsp() {
		return isp;
	}

	public void setIsp(String isp) {
		this.isp = isp;
	}

}