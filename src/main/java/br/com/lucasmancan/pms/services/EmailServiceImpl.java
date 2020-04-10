package br.com.lucasmancan.pms.services;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.models.MailMessage;
import br.com.lucasmancan.pms.services.interfaces.EmailService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {

//    private JavaMailSender emailSender;
//
//    @Autowired
//    public EmailServiceImpl(JavaMailSender emailSender) {
//        this.emailSender = emailSender;
//    }

    @Override
    public void send(MailMessage mailMessage) throws AppException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailMessage.getEmail());
        message.setSubject(mailMessage.getSubject());
        message.setText(mailMessage.getBody());

        try{

//            emailSender.send(message);
        }catch (Exception e){
            log.log(Level.ERROR, "" + e.getMessage(), e);

            throw new AppException("Some error occurred while tryng to send your message. Try again later and check the e-mail address registry..");
        }

    }

}