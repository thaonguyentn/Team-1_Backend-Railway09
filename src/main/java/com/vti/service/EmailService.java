package com.vti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.vti.entity.Account;
import com.vti.repository.IRegistrationUserTokenRepository;

@Component
public class EmailService implements IEmailService {

	@Autowired
	private IAccountService accountService;
	
	@Autowired
	private IRegistrationUserTokenRepository registrationUserTokenRepository;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Function gửi mail kích hoạt tài khoản
	 */

	@Override
	public void sendRegistrationUserConfirm(String email,String token) {
		Account account = accountService.getAccountByEmail(email);
//		String token = registrationUserTokenRepository.findByUserId(account.getAccountId());
//		String confirmationUrl = "http://localhost:8080/api/v3/register/activeUser?token=" + token;
		String confirmationUrl = "https://the-gioi-cam-tay-fe.herokuapp.com/activeUser?token=" + token;
		
		String subject = "Xác Nhận Đăng Ký Account";
		String content = "Bạn đã đăng ký thành công. Click vào link dưới đây để kích hoạt tài khoản \n"
				+ confirmationUrl;

		sendEmail(email, subject, content);

	}
	
	/**
	 * Function gửi mail thông báo đơn hàng được duyệt
	 */
	
	@Override
	public void sendOrderConfirm(String email) {
		
		String subject = "Xác Nhận Đặt Hàng";
		String content = "Bạn đã đặt hàng thành công. Hàng của bạn sẽ được chuyển đến sau 3 ngày. "
				+ "Nếu có thay đổi, chúng tôi sẽ thông báo cho bạn qua email hoặc điện thoại. Xin cám ơn vì đã ủng hộ shop \n";
		
		sendEmail(email, subject, content);
	}
	
	/**
	 * Function gửi mail thông báo đơn hàng bị hủy
	 */
	
	@Override
	public void sendOrderConfirmEnd(String email) {
		
		String subject = "Thông Báo Hủy Đơn Hàng";
		String content = "Đơn hàng của bạn đã bị hủy. Nếu có thắc mắc xin liên hệ tổng đài 19001009 "
				+ "Để biết thêm chi tiết. Xin chân thành xin lỗi vì sự bất tiện này \n";
		
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
