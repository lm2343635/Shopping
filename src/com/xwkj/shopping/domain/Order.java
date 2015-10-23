package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
	
	private static final long serialVersionUID = 1740553232189480871L;

	private String oid;
	private Date createDate;
	private Boolean express;
	private Double amount;
}
