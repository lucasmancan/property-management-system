package br.com.lucasmancan.pms.controllers;


import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.models.AppResponse;
import br.com.lucasmancan.pms.models.dto.BrandDTO;
import br.com.lucasmancan.pms.services.interfaces.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api("Registro e atualização de marcas.")
@RequestMapping("api/brands")
public class BrandController {

    private BrandService brandService;

    @Autowired
    public BrandController(BrandService service){
        this.brandService = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Brand was created"),
            @ApiResponse(code = 400, message = "Invalid data")
    })
    @ApiOperation(value = "Cria uma nova marca.", response = AppResponse.class)
    public AppResponse save(@RequestBody @Valid BrandDTO brandDTO) throws AppException {
        return AppResponse.valueOf("Brand was created",  brandService.save(brandDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Brand was inactivated."),
            @ApiResponse(code = 400, message = "Invalid brand")
    })
    @ApiOperation(value = "Inativa marca.", response = AppResponse.class)
    public AppResponse inactivate(@PathVariable("id") Long id) throws AppException {
        brandService.inactivate(id);
        return AppResponse.valueOf("Brand was inactivated.");
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Brand was updated."),
            @ApiResponse(code = 400, message = "Invalid brand")
    })
    @ApiOperation(value = "Atualiza uma marca.", response = AppResponse.class)
    public AppResponse update(@PathVariable("id") Long id, @RequestBody @Valid BrandDTO brandDTO) throws AppException {
        return AppResponse.valueOf("Brand was updated.", brandService.update(id, brandDTO));
    }

    @GetMapping("{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Brand has found.")
    })
    @ApiOperation(value = "Busca uma marca específica.", response = AppResponse.class)
    public AppResponse get(@PathVariable("id") Long id) throws AppException {
        return AppResponse.valueOf("Brand was found.",  brandService.findById(id));
    }

    @GetMapping
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
    })
    @ApiOperation(value = "Busca todas as marcas da conta.", response = AppResponse.class)
    public AppResponse getAll() {
        return AppResponse.valueOf("",  brandService.findAll());
    }


}
