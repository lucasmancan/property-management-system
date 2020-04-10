package br.com.lucasmancan.pms.models.dto;

import br.com.lucasmancan.pms.models.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PropertyDTO implements AppDTO{
    private Long id;
    private String code;


    @NotNull(message = "Property's name must not be null")
    private String name;

    @NotNull(message = "Brand's id must not be null")
    private Long brandId;

    private String brandName;
    private Status status;
    private String description;
}
