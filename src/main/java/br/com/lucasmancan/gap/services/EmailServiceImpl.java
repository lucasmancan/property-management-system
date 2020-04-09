package br.com.lucasmancan.gap.services;

import br.com.lucasmancan.gap.models.MailMessage;
import br.com.lucasmancan.gap.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(MailMessage mailMessage) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailMessage.getEmail());
        message.setSubject(mailMessage.getSubject());
        message.setText(mailMessage.getBody());
        emailSender.send(message);
    }

}