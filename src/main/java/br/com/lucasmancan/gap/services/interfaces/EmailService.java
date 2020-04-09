package br.com.lucasmancan.gap.services.interfaces;

import br.com.lucasmancan.gap.models.MailMessage;
import org.springframework.stereotype.Service;

public interface EmailService {
    void send(MailMessage mailMessage);
}
