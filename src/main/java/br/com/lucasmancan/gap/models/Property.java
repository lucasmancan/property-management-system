package br.com.lucasmancan.gap.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "properties")
@EqualsAndHashCode(callSuper = false, exclude = {"brand", "user"})
@ToString(callSuper = false, exclude = {"brand", "user"})
public class Property implements AppModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    @JoinColumn(name = "brand_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
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
