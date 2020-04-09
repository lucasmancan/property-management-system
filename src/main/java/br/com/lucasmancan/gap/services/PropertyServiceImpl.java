package br.com.lucasmancan.gap.services;

import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.Property;
import br.com.lucasmancan.gap.models.Status;
import br.com.lucasmancan.gap.models.dto.PropertyDTO;
import br.com.lucasmancan.gap.repositories.PropertyRepository;
import br.com.lucasmancan.gap.services.interfaces.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    private PropertyRepository repository;

    private ModelMapper mapper;

    @Autowired
    public PropertyServiceImpl(PropertyRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PropertyDTO save(PropertyDTO propertyDTO) {
        var property = this.convert(propertyDTO);

        UUID uuid = UUID.randomUUID();

        property.setCode(uuid.toString().substring(0,8));

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


        // TODO GET FROM CONTEXT
        long userId = 1L;
        return this.repository.findAllByUserId(userId)
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

        return this.convert(property);
    }

    @Override
    public PropertyDTO findById(Long id) throws AppNotFoundException {
        return this.convert(find(id));
    }

    @Override
    public void inactivate(Long id) throws AppNotFoundException {

        var property = find(id);

        property.setStatus(Status.inactive);

        repository.save(property);
    }
}
