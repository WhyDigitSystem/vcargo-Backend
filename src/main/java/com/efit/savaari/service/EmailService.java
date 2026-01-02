package com.efit.savaari.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendOtpEmail(String to, String subject, String otp) throws MessagingException, IOException {

		// Load HTML template from classpath (JAR-safe)
		ClassPathResource resource = new ClassPathResource("templates/otp-template.html");

		String html;
		try (InputStream inputStream = resource.getInputStream()) {
			html = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		}

		// Replace OTP placeholder
		html = html.replace("{{OTP}}", otp);

		// Prepare email
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				"UTF-8");

		helper.setFrom("justinaravinth2@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(html, true); // HTML enabled

		mailSender.send(message);
	}

	public void sendAuctionMail(String bccEmailIds, String htmlBody) throws MessagingException, IOException {

		if (bccEmailIds == null || bccEmailIds.isEmpty()) {
			return; // safety
		}

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom("noreply@whydigit.in");
		helper.setBcc(bccEmailIds.split(",")); // ✅ multiple BCC
		helper.setSubject("New Auction Created – Quote Required");
		helper.setText(htmlBody, true);
		mailSender.send(message);
	}

}
