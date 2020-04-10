package br.com.lucasmancan.pms.services;


import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.exceptions.AppNotFoundException;
import br.com.lucasmancan.pms.models.Brand;
import br.com.lucasmancan.pms.models.Status;
import br.com.lucasmancan.pms.models.dto.BrandDTO;
import br.com.lucasmancan.pms.repositories.BrandRepository;
import br.com.lucasmancan.pms.services.interfaces.BrandService;
import br.com.lucasmancan.pms.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository repository;

    private UserService userService;

    private ModelMapper mapper;

    @Autowired
    public BrandServiceImpl(BrandRepository repository, ModelMapper mapper, UserService userService) {
        this.repository = repository;
        this.mapper = mapper;
        this.userService = userService;
    }

    public BrandDTO save(BrandDTO brandDTO) {
        var brand = this.convert(brandDTO);


        brand.setUser(userService.getCurrentUser());
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
        return this.repository.findAllByUserId(userService.getCurrentUser().getId())
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
    public void inactivate(Long id) throws AppException {

        var brand = find(id);

        if(!brand.getUser().equals(userService.getCurrentUser()))
            throw new AppException("Can not inactivate another user's brand.");

        brand.setStatus(Status.inactive);

        repository.save(brand);
    }
}
