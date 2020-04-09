package br.com.lucasmancan.gap.services;


import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.Brand;
import br.com.lucasmancan.gap.models.Status;
import br.com.lucasmancan.gap.models.dto.BrandDTO;
import br.com.lucasmancan.gap.repositories.BrandRepository;
import br.com.lucasmancan.gap.services.interfaces.BrandService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository repository;

    private ModelMapper mapper;

    @Autowired
    public BrandServiceImpl(BrandRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BrandDTO save(BrandDTO brandDTO) {
        var brand = this.convert(brandDTO);

        brand = repository.save(brand);

        return this.convert(brand);
    }

    private BrandDTO convert(Brand brand) {
        return this.mapper.map(brand, BrandDTO.class);
    }

    private Brand convert(BrandDTO brandDTO) {
        return this.mapper.map(brandDTO, Brand.class);
    }

    @Override
    public List<BrandDTO> findAll() {
        return this.repository.findAll()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Brand find(Long id) throws AppNotFoundException {
        return this.repository.findById(id).orElseThrow(AppNotFoundException::new);
    }

    @Override
    public BrandDTO update(Long id, BrandDTO brandDTO) throws AppNotFoundException {
        var brand = find(id);

        brand.setName(brandDTO.getName());

        return this.convert(brand);
    }

    @Override
    public BrandDTO findById(Long id) throws AppNotFoundException {
        return this.convert(find(id));
    }

    @Override
    public void inactivate(Long id) throws AppNotFoundException {

        var brand = find(id);

        brand.setStatus(Status.inactive);

        repository.save(brand);
    }
}
