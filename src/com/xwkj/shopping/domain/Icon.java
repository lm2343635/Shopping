package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Icon implements Serializable {
	
	private static final long serialVersionUID = -1464814977780730659L;
	
	private String iid;
	private String filename;
	private Date upload;
	
	public String getIid() {
		return iid;
	}
	public String getFilename() {
		return filename;
	}
	public Date getUpload() {
		return upload;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setUpload(Date upload) {
		this.upload = upload;
	}
}
