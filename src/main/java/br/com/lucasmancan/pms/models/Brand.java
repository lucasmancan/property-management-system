package br.com.lucasmancan.pms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "brands")
@AllArgsConstructor
@NoArgsConstructor
public class Brand implements AppModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private AppUser user;

    @PreUpdate
    void update(){
        this.updatedAt = LocalDateTime.now();
    }


    @PrePersist
    void persist(){
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();

        if(this.status == null)
            this.status = Status.active;
    }
}
