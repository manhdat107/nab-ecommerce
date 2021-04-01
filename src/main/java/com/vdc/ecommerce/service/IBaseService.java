package com.vdc.ecommerce.service;

import com.vdc.ecommerce.model.response.JsonResponse;

import java.util.List;

/**
 * Is base service for child service other
 * Providing some necessary functions
 *
 * @param <D>  DTO data for endpoints
 * @param <ID> DTO data's id type
 */
public interface IBaseService<D, ID> {
    JsonResponse<List<D>> create(List<D> DTOs);

    JsonResponse<D> add(D DTO);

    JsonResponse<D> update(D DTO);

    JsonResponse<List<D>> getAll(Integer pageNum, Integer pageSize);

    JsonResponse<D> getById(ID id);

    JsonResponse<String> deleteById(ID id);

}
