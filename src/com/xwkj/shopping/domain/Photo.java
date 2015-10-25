package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Photo implements Serializable {

	private static final long serialVersionUID = 4073354306938399726L;
	
	private String pid;
	private String filename;
	private Date upload;
	private Good good;
	
	public String getPid() {
		return pid;
	}
	public String getFilename() {
		return filename;
	}
	public Date getUpload() {
		return upload;
	}
	public Good getGood() {
		return good;
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
	public void setGood(Good good) {
		this.good = good;
	}
	
}
