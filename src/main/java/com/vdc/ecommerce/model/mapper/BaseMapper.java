package com.vdc.ecommerce.model.mapper;

import java.util.List;
/**
 * @param <E> : source
 * @param <D> : target
 */
public interface BaseMapper<E, D> {
    D toDTO(E e);

    List<D> toDTOs(List<E> eList);

    E toEntity(D d);

    List<E> toEntities(List<D> dList);
}



