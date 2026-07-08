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

		helper.setFrom("info@whydigit.com");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(html, true); // HTML enabled

		ClassPathResource logo = new ClassPathResource("templates/VCARGO-updated-logo.png");
	    helper.addInline("vcargoLogo", logo);
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
	
	/**
     * Send an HTML formatted email
     */
    public void sendHtmlEmail(String fromMail,String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessage.addHeader("Auto-Submitted", "auto-generated");
            mimeMessage.addHeader("Precedence", "bulk");
            mimeMessage.addHeader("X-Auto-Response-Suppress", "All");

            helper.setFrom(fromMail,"WHY DIGIT SYSTEMS (No Reply)");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            System.out.println("✅ HTML Mail sent successfully to " + toEmail);

        } catch (MessagingException e) {
            System.err.println("❌ Messaging error while sending HTML mail: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Failed to send HTML mail to " + toEmail + ": " + e.getMessage());
            e.printStackTrace();
        }
    }



	public void sendSimpleEmail(String toEmail, String subject, String body) {
		// TODO Auto-generated method stub
		
	}

}
