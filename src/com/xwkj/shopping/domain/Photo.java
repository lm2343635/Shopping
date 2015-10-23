package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Photo implements Serializable {

	private static final long serialVersionUID = 4073354306938399726L;
	
	private String pid;
	private String filename;
	private Date upload;
	private Good good;
	
}
