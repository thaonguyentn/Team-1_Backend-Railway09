package com.vti.event;

import org.springframework.context.ApplicationEvent;

public class OnSendRegistrationUserConfirmViaEmailEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String email;
	private String token;
	public OnSendRegistrationUserConfirmViaEmailEvent(String email) {
		super(email);
		this.email = email;
	}
	public OnSendRegistrationUserConfirmViaEmailEvent(String email,String token) {
		super(email);
		this.email = email;
		this.setToken(token);
	}
	public String getEmail() {
		return email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
