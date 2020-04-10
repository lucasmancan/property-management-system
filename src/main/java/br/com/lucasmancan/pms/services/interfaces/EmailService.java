package br.com.lucasmancan.pms.services.interfaces;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.models.MailMessage;

public interface EmailService {
    void send(MailMessage mailMessage) throws AppException;
}
