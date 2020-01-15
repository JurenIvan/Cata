package hr.fer.projekt.cata.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Autowired
	private JavaMailSender emailSender;

	public EmailSender(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendMessage(String to, String subject, String text) {
		emailSender.send(createMessage(to, subject, text));
	}

	private SimpleMailMessage createMessage(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		return message;
	}
}
