package br.com.lucasmancan.gap.controllers;


import br.com.lucasmancan.gap.exceptions.AppException;
import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.AppResponse;
import br.com.lucasmancan.gap.models.dto.PropertyDTO;
import br.com.lucasmancan.gap.services.interfaces.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("properties")
public class PropertyController {

    private PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService service){
        this.propertyService = service;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse save(@RequestBody PropertyDTO propertyDTO) throws AppException {
        return AppResponse.valueOf("Property was created",  propertyService.save(propertyDTO));
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public AppResponse inactivate(@RequestParam("id") Long id) throws AppNotFoundException {
        propertyService.inactivate(id);
        return AppResponse.valueOf("Property has been inactivated.");
    }

    @PutMapping("{id}")
    @ResponseBody
    public AppResponse update(@RequestParam("id") Long id, @RequestBody PropertyDTO propertyDTO) throws AppNotFoundException {
        return AppResponse.valueOf("Property has been inactivated.", propertyService.update(id, propertyDTO));
    }

    @GetMapping("{id}")
    @ResponseBody
    public AppResponse get(@RequestParam("id") Long id) throws AppNotFoundException {
        return AppResponse.valueOf("Property has been inactivated.",  propertyService.findById(id));
    }

    @GetMapping
    @ResponseBody
    public AppResponse getAll() {
        return AppResponse.valueOf("Property has been inactivated.",  propertyService.findAll());
    }
}
