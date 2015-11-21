package com.xwkj.shopping.service;

public interface MailManager {
	
	boolean createOrder(String oid, String email);
	
	boolean sendGood(String oid, String email);
	
	boolean receiveGood(String oid, String email);
}
