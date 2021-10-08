package com.vti.service;

public interface IEmailService {
	
	public void sendOrderConfirm(String email);
	
	public void sendOrderConfirmEnd(String email);

	/**
	 * Function gửi mail kích hoạt tài khoản
	 */
	void sendRegistrationUserConfirm(String email,String token);

}
