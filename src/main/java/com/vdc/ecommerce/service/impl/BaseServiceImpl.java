package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.model.CommonEntity;
import com.vdc.ecommerce.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


public abstract class BaseServiceImpl<E extends CommonEntity, ID extends Number> implements BaseService<E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    private final JpaRepository<E, ID> repository;

    public BaseServiceImpl(JpaRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<E> addMultiple(List<E> entities, int batchSize) {
        return null;
    }

    @Override
    public E add(E entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public E update(E entity) {
        Optional<E> eOptional = repository.findById((ID) entity.getId());
        if (eOptional.isPresent()) {
            E eSave = eOptional.get();
            repository.saveAndFlush(eSave);
            return entity;
        }
        throw new NoSuchElementException("No value present");
    }

    @Override
    public List<E> getAll(Pageable pageable) {
        if (pageable == null) {
            //just get 10 first when default
            pageable = PageRequest.of(0, 10);
        }
        return repository.findAll(pageable).get().collect(Collectors.toList());
    }

    @Override
    public E getById(ID id) {
        return null;
    }

    @Override
    public void deleteById(ID id) {
        if(id == null) {
            LOGGER.info("id is empty");
            return;
        }
        repository.deleteById(id);
    }
}
