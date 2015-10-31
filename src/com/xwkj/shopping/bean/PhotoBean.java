package com.xwkj.shopping.bean;

import java.util.Date;

import com.xwkj.shopping.domain.Photo;

public class PhotoBean {
	
	private String pid;
	private String filename;
	private Date upload;
	
	public String getPid() {
		return pid;
	}
	public String getFilename() {
		return filename;
	}
	public Date getUpload() {
		return upload;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setUpload(Date upload) {
		this.upload = upload;
	}
	
	public PhotoBean(Photo photo) {
		super();
		this.pid = photo.getPid();
		this.filename = photo.getFilename();
		this.upload = photo.getUpload();
	}

}
