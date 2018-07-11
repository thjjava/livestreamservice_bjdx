package com.sttri.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * 敏感词库表
 */
@Entity
@Table(name = "sensitive_word")
public class SensitiveWord implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String sensitiveWord;
	private String addTime;
	
	public SensitiveWord() {
	}
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "SensitiveWord", length = 255)
	public String getSensitiveWord() {
		return sensitiveWord;
	}

	public void setSensitiveWord(String sensitiveWord) {
		this.sensitiveWord = sensitiveWord;
	}
	
	@Column(name = "AddTime", length = 20)
	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

}