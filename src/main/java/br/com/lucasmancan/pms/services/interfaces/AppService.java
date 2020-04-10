package br.com.lucasmancan.pms.services.interfaces;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.models.dto.AppDTO;

import java.util.List;

public interface AppService<T extends AppDTO> {

    T save(T dto) throws AppException;

    T update(Long id, T dto) throws AppException;

    T findById(Long id) throws AppException;

    void inactivate(Long id) throws AppException;

    List<T> findAll();
}
