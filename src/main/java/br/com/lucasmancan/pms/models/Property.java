package br.com.lucasmancan.pms.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "properties")
@EqualsAndHashCode(callSuper = false, exclude = {"brand", "user"})
@ToString(exclude = {"brand", "user"})
public class Property implements AppModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @NotNull(message = "Name must not be null")
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
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
