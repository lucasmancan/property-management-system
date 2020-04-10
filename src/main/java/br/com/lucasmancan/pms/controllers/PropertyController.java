package br.com.lucasmancan.pms.controllers;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.models.AppResponse;
import br.com.lucasmancan.pms.models.dto.PropertyDTO;
import br.com.lucasmancan.pms.services.interfaces.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Api("Registro e atualização de propriedades.")
@RequestMapping("api/properties")
public class PropertyController {

    private PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService service){
        this.propertyService = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Property was created"),
            @ApiResponse(code = 400, message = "Invalid data")
    })
    @ApiOperation(value = "Cria uma nova propriedade.", response = AppResponse.class)
    public AppResponse save(@RequestBody PropertyDTO propertyDTO) throws AppException {
        return AppResponse.valueOf("Property was created",  propertyService.save(propertyDTO));
    }

    @DeleteMapping("{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property was inactivated."),
            @ApiResponse(code = 400, message = "Invalid property")
    })
    @ApiOperation(value = "Inativa propriedade.", response = AppResponse.class)
    public AppResponse inactivate(@PathVariable("id") Long id) throws AppException {
        propertyService.inactivate(id);
        return AppResponse.valueOf("Property was inactivated.");
    }

    @PutMapping("{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property was updated."),
            @ApiResponse(code = 400, message = "Invalid property")
    })
    @ApiOperation(value = "Atualiza uma propriedade.", response = AppResponse.class)
    public AppResponse update(@PathVariable("id") Long id, @RequestBody PropertyDTO propertyDTO) throws AppException {
        return AppResponse.valueOf("Property was updated.", propertyService.update(id, propertyDTO));
    }

    @GetMapping("{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property has found.")
    })
    @ApiOperation(value = "Busca uma propriedade específica.", response = AppResponse.class)
    public AppResponse get(@PathVariable("id") Long id) throws AppException {
        return AppResponse.valueOf("Property has found.",  propertyService.findById(id));
    }

    @GetMapping
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
    })
    @ApiOperation(value = "Busca todas as propriedades da conta.", response = AppResponse.class)
    public AppResponse getAll() {
        return AppResponse.valueOf("",  propertyService.findAll());
    }
    
}
