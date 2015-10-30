package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Icon;

public class IconBean {
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
	
	public IconBean(Icon icon) {
		super();
		this.iid = icon.getIid();
		this.filename = icon.getFilename();
		this.upload = icon.getUpload();
	}
	
}
