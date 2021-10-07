package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vti.entity.Account;
import com.vti.repository.IResetPasswordTokenRepository;

@Service
public class ResetPasswordService implements IResetPasswordService{

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IResetPasswordTokenRepository resetPasswordRepo;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendResetPasswordConfirm(String email) {
		Account account = accountService.getAccountByEmail(email);
		String token = resetPasswordRepo.findByUserId(account.getAccountId());
		
//		String confirmationUrl = "http://localhost:8080/api/v3/resetpassword/reset?token=" + token;
		String confirmationUrl = "https://the-gioi-cam-tay-fe.herokuapp.com/resetPassword?token=" + token;
		
		String subject = "Xác Nhận Reset Password";
		String content = "Bạn đã reset mật khẩu thành công. Click vào link dưới đây để set password về mặc định (123456) \n"
				+ confirmationUrl;
		
		sendEmail(email, subject, content);
		
	}
	
	private void sendEmail(final String recipientEmail, final String subject, final String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipientEmail);
		message.setSubject(subject);
		message.setText(content);

		mailSender.send(message);
	}
}
