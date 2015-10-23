package com.xwkj.shopping.domain;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {

	private static final long serialVersionUID = 7906540632450428382L;

	private String cid;
	private String cname;
	private Date createDate;
	private Integer goods;
	private Type type;
	private Icon icon;
}
