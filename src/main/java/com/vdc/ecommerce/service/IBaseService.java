package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.response.JsonResponseEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Is base service for child service other
 * Providing some necessary functions
 *
 * @param <D>  DTO data for endpoints
 * @param <ID> DTO data's id type
 */
public interface IBaseService<D, ID> {
    JsonResponseEntity<List<D>> create(List<D> DTOs);
    JsonResponseEntity<D> add(D DTO);
    JsonResponseEntity<D> update(D DTO);
    JsonResponseEntity<List<D>> getAll(Integer pageNum, Integer pageSize);
    JsonResponseEntity<D> getById(ID id);
    JsonResponseEntity<String> deleteById(ID id);
}
