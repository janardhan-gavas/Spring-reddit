package com.boot.springReddit.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.boot.springReddit.exception.SpringRedditException;
import com.boot.springReddit.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class MailService {
	
	private final JavaMailSender javaMailSender;
	private final MailContentBuilder mailContentBuilder;
	
	@Async
	void sendMail(NotificationEmail notificationEmail) {
		
		MimeMessagePreparator mimeMessagePreparator = mimeMessage ->{
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("redditboot@gmail.com");
			messageHelper.setTo(notificationEmail.getRecipient());
			messageHelper.setSubject(notificationEmail.getSubject());
			messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
		};
		
		try {
			javaMailSender.send(mimeMessagePreparator);
			log.info("Activation Email Sent!!");
		} catch (MailException e) {
			//e.printStackTrace();
			throw new SpringRedditException("Exception occured while sending the Email to "+notificationEmail.getRecipient());
		}
	}
}
