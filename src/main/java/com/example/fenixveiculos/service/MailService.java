package com.example.fenixveiculos.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.from}")
	private String from;

	@Value("${server.base-url}")
	private String baseUrl;

	public void sendSimpleMessage(
			String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}

	public void sendRecoveryPassword(String to, String token) {
		String href = baseUrl + "/auth/recoveryPassword/" + token;
		String text = "<span>Acesse para recuperar sua senha: </span>"
				.concat("<a href=\"" + href + "\">Recuperar senha</a>");

		sendHtmlMail(to, "Recuperação de senha", text);
	}

	public void sendHtmlMail(String to, String subject, String text) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
					false, "UTF-8");

			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(text, true);

			mailSender.send(mimeMessage);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
