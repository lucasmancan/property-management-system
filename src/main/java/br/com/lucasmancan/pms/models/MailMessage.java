package br.com.lucasmancan.pms.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailMessage {
    private String subject;
    private String body;
    private String email;


}
