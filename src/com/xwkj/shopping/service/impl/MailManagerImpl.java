package com.xwkj.shopping.service.impl;

import com.xwkj.common.util.MailService;
import com.xwkj.shopping.domain.Order;
import com.xwkj.shopping.service.MailManager;
import com.xwkj.shopping.service.util.ManagerTemplate;

public class MailManagerImpl extends ManagerTemplate implements MailManager {
	
	private String SMTPServer;
	private String username;
	private String password;
	private String subject;
	private String ExpressTure;
	private String ExpressFalse;
	private String createMail;
	private String sendMail;
	private String receiveMail;

	public void setSMTPServer(String sMTPServer) {
		SMTPServer = sMTPServer;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setExpressTure(String expressTure) {
		ExpressTure = expressTure;
	}

	public void setExpressFalse(String expressFalse) {
		ExpressFalse = expressFalse;
	}

	public void setCreateMail(String createMail) {
		this.createMail = createMail;
	}

	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}

	public void setReceiveMail(String receiveMail) {
		this.receiveMail = receiveMail;
	}

	@Override
	public boolean createOrder(String oid, String email) {
		Order order=orderDao.get(oid);
		if(order==null)
			return false;
		String content=createMail.replace("#name#", order.getName())
				.replace("#ono#", order.getOno())
				.replace("#count#", order.getCount().toString())
				.replace("#amount#", order.getAmount().toString())
				.replace("#express#", order.getExpress()? ExpressTure: ExpressFalse)
				.replace("#telephone#", order.getTelephone())
				.replace("#address#", order.getAddress())
				.replace("#zip#", order.getZip());
		return send(email, content);
	}

	@Override
	public boolean sendGood(String oid, String email) {
		return send(email, sendMail);
	}

	@Override
	public boolean receiveGood(String oid, String email) {
		return send(email, receiveMail);
	}

	private boolean send(String email, String content) {
		MailService mailService=new MailService();
		mailService.setSmtpServer(SMTPServer);
		mailService.setUsername(username);
		mailService.setPassword(password);
		mailService.setTo(email);
		mailService.setFrom(username);
		mailService.setSubject(subject);
		mailService.setContent(content);
		return mailService.send();
	}
}
