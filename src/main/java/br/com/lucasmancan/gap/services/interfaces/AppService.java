package br.com.lucasmancan.gap.services.interfaces;

import br.com.lucasmancan.gap.exceptions.AppException;
import br.com.lucasmancan.gap.exceptions.AppNotFoundException;
import br.com.lucasmancan.gap.models.dto.AppDTO;

import java.util.List;

public interface AppService<T extends AppDTO> {

    T save(T dto) throws AppException;

    T update(Long id, T dto) throws AppNotFoundException;

    T findById(Long id) throws AppNotFoundException;

    void inactivate(Long id) throws AppNotFoundException;

    List<T> findAll();
}
