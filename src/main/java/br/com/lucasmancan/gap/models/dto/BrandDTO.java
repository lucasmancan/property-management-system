package br.com.lucasmancan.gap.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO implements AppDTO {
    private Long id;
    private String name;
}
