package br.com.lucasmancan.gap.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;


    @PrePersist
    void persist(){
        this.createdAt = LocalDateTime.now();
    }
}
