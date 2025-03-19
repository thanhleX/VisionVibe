package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    @Value("${spring.mail.username}")
    private String senderMail;
    private final JavaMailSender mailSender;

    public void sendOrderMail(String to, String subject, String htmlContent, Long billId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderMail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(String.format(htmlContent, billId), true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new AppException(ErrorCode.CANT_SEND_MAIL);
        }
    }
}
