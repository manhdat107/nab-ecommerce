package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.response.ResponseModel;

import java.util.List;

/**
 * Is base service for child service other
 * Providing some necessary functions
 *
 * @param <D>  DTO data for endpoints
 * @param <ID> DTO data's id type
 */
public interface IBaseService<D, ID> {
    ResponseModel<List<D>> create(List<D> DTOs);

    ResponseModel<D> add(D DTO);

    ResponseModel<D> update(D DTO);

    ResponseModel<List<D>> getAll(Integer pageNum, Integer pageSize);

    ResponseModel<D> getById(ID id);

    ResponseModel<String> deleteById(ID id);

}
