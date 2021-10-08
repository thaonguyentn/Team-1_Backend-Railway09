package com.vti.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import com.vti.event.OnSendRegistrationUserConfirmViaEmailEvent;
import com.vti.service.EmailService;


public class SendRegistrationUserConfirmViaEmailListener implements ApplicationListener<OnSendRegistrationUserConfirmViaEmailEvent> {

	@Autowired
	private EmailService emailService;

	@Override
	public void onApplicationEvent(OnSendRegistrationUserConfirmViaEmailEvent event) {
		sendConfirmViaEmail(event.getEmail(),event.getToken());
	}

	private void sendConfirmViaEmail(String email,String token) {
		emailService.sendRegistrationUserConfirm(email, token);
	}
}