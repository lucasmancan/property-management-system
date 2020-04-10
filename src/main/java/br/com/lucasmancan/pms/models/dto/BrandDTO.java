package br.com.lucasmancan.pms.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO implements AppDTO {
    private Long id;

    @NotNull(message = "Name must not be null")
    private String name;
}
