package br.com.lucasmancan.gap.models.dto;

import br.com.lucasmancan.gap.models.Status;
import lombok.Data;

@Data
public class PropertyDTO implements AppDTO{
    private Long id;
    private Long code;
    private String name;
    private Long brandId;
    private String brandName;
    private Status status;
    private String description;
}
