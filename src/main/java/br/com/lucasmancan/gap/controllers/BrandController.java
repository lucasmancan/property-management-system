package br.com.lucasmancan.gap.controllers;


import br.com.lucasmancan.gap.exceptions.AppException;
import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.AppResponse;
import br.com.lucasmancan.gap.models.dto.BrandDTO;
import br.com.lucasmancan.gap.services.interfaces.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brands")
public class BrandController {

    private BrandService brandService;

    @Autowired
    public BrandController(BrandService service){
        this.brandService = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse save(@RequestBody BrandDTO brandDTO) throws AppException {
        return AppResponse.valueOf("Brand was created",  brandService.save(brandDTO));
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public AppResponse inactivate(@RequestParam("id") Long id) throws AppNotFoundException {
        brandService.inactivate(id);
        return AppResponse.valueOf("Brand has been inactivated.");
    }

    @PutMapping("{id}")
    @ResponseBody
    public AppResponse update(@RequestParam("id") Long id, @RequestBody BrandDTO brandDTO) throws AppNotFoundException {
        return AppResponse.valueOf("Brand has been inactivated.", brandService.update(id, brandDTO));
    }

    @GetMapping("{id}")
    @ResponseBody
    public AppResponse get(@RequestParam("id") Long id) throws AppNotFoundException {
        return AppResponse.valueOf("Brand has been inactivated.",  brandService.findById(id));
    }

    @GetMapping
    @ResponseBody
    public AppResponse getAll() {
        return AppResponse.valueOf("Brand has been inactivated.",  brandService.findAll());
    }


}
