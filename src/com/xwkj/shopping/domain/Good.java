package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class Good implements Serializable {

	private static final long serialVersionUID = -3514204735446716317L;
	
	private String gid;
	private String gname;
	private Double price;
	private Integer number;
	private Integer sold;
	private Date createDate;
	private Category category;
	private Photo cover;
	private Set<Photo> photos;

}
