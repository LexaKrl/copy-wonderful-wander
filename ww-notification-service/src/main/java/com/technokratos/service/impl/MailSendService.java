package com.technokratos.service.impl;

import com.technokratos.config.property.CustomMailProperties;
import com.technokratos.exception.SendMessageException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender mailSender;
    private final CustomMailProperties customMailProperties;
    private final MailProperties mailProperties;
    private final SpringTemplateEngine templateEngine;

    @Async
    @Retryable(
            retryFor = { MessagingException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public void sendEmail(String to, String subject, Map<String, Object> variables) {
        try {
            Context context = new Context();
            context.setVariables(variables);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            String html = templateEngine.process("email", context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            helper.setFrom(new InternetAddress(
                    mailProperties.getUsername(),
                    customMailProperties.getFrom()
            ));

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new SendMessageException("Error during email sending");
        }
    }
}
