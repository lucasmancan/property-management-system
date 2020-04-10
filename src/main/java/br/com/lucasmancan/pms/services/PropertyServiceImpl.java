package br.com.lucasmancan.pms.services;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.exceptions.AppNotFoundException;
import br.com.lucasmancan.pms.models.Property;
import br.com.lucasmancan.pms.models.Status;
import br.com.lucasmancan.pms.models.dto.PropertyDTO;
import br.com.lucasmancan.pms.repositories.BrandRepository;
import br.com.lucasmancan.pms.repositories.PropertyRepository;
import br.com.lucasmancan.pms.services.interfaces.PropertyService;
import br.com.lucasmancan.pms.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private UserService userService;

    private PropertyRepository repository;

    private BrandRepository brandRepository;

    private ModelMapper mapper;

    @Autowired
    public PropertyServiceImpl(PropertyRepository repository, ModelMapper mapper, UserService userService, BrandRepository brandRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
        this.brandRepository = brandRepository;
    }

    public PropertyDTO save(PropertyDTO propertyDTO) {
        var property = this.convert(propertyDTO);

        UUID uuid = UUID.randomUUID();

        property.setCode(uuid.toString());

        property.setUser(userService.getCurrentUser());

        var brand = brandRepository.findById(userService.getCurrentUser().getId(), propertyDTO.getBrandId())
                .orElse(null);

        property.setBrand(brand);

        property = repository.save(property);

        return this.convert(property);
    }

    private PropertyDTO convert(Property property) {
        return this.mapper.map(property, PropertyDTO.class);
    }

    private Property convert(PropertyDTO propertyDTO) {
        return this.mapper.map(propertyDTO, Property.class);
    }

    @Override
    public List<PropertyDTO> findAll() {
        return this.repository.findAllByUserId(userService.getCurrentUser().getId())
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Property find(Long id) throws AppNotFoundException {
        return this.repository.findById(id).orElseThrow(AppNotFoundException::new);
    }

    @Override
    public PropertyDTO update(Long id, PropertyDTO propertyDTO) throws AppNotFoundException {
        var property = find(id);

        property.setName(propertyDTO.getName());
        property.setDescription(propertyDTO.getDescription());

        var brand = brandRepository.findById(userService.getCurrentUser().getId(), propertyDTO.getBrandId())
                .orElse(null);

        property.setBrand(brand);

        property = repository.save(property);


        return this.convert(property);
    }

    @Override
    public PropertyDTO findById(Long id) throws AppNotFoundException {
        return this.convert(find(id));
    }

    @Override
    public void inactivate(Long id) throws AppException {

        var property = find(id);

        if (!property.getUser().equals(userService.getCurrentUser()))
            throw new AppException("Can not inactivate another user's property.");

        property.setStatus(Status.inactive);

        repository.save(property);
    }
}
