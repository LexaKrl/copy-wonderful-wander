package com.technokratos.service.impl;

import com.technokratos.config.property.CustomMailProperties;
import com.technokratos.entity.UserData;
import com.technokratos.exception.SendMessageException;
import com.technokratos.service.NotificationSender;
import com.technokratos.templates.NotificationTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailSendService implements NotificationSender {

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
    @Override
    public void sendNotification(UserData userData, NotificationTemplate template, Map<String, String> vars) {
        if (userData == null) {log.warn("UserData is null"); return;}
        String to = userData.getEmail();
        if (to == null || to.isBlank()) {
            log.warn("Email is empty for user: {}", userData.getUserId());
            return;
        }

        Context context = new Context();
        context.setVariables(new HashMap<>(vars));
        context.setVariable("title", template.getTitle(vars));
        context.setVariable("message", template.getBody(vars));
        context.setVariable("link", template.getActionUrl(vars));

        String html = templateEngine.process("email", context);

        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject(template.getTitle(vars));
            helper.setText(html, true);
            helper.setFrom(new InternetAddress(mailProperties.getUsername(), customMailProperties.getFrom()));

            mailSender.send(msg);
        } catch (MessagingException
                | UnsupportedEncodingException e) {
            throw new SendMessageException("Email send error: " + e.getMessage());
        }
    }

    @Override
    public void sendManyNotifications(List<UserData> userDataList, NotificationTemplate template, Map<String, String> vars) {
        for (UserData userData : userDataList) {
            sendNotification(userData, template, vars);
        }
    }
}
