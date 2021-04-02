package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.PageConstant;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.BaseEntity;
import com.vdc.ecommerce.model.dto.BaseDTO;
import com.vdc.ecommerce.model.mapper.BaseMapper;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.model.response.ResponsePageableModel;
import com.vdc.ecommerce.service.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Base of ServiceCoreImpl to handle common http interactive
 *
 * @param <E>  Entity
 * @param <D>  DTO
 * @param <ID> ID Type
 */
public abstract class BaseServiceImpl<E extends BaseEntity<ID>, D extends BaseDTO<ID>, ID extends Long> implements IBaseService<D, ID> {

    protected JpaRepository<E, ID> repo;
    protected BaseMapper<E, D> mapper;
    protected AppUtils appUtils;

    public BaseServiceImpl(JpaRepository<E, ID> repo, BaseMapper<E, D> mapper, AppUtils appUtils) {
        this.repo = repo;
        this.mapper = mapper;
        this.appUtils = appUtils;
    }

    @Override
    public ResponseModel<List<D>> create(List<D> dList) {
        List<E> eList = mapper.toEntities(dList);
        repo.saveAll(eList);
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public ResponseModel<D> add(D DTO) {
        E entity = mapper.toEntity(DTO);
        repo.save(entity);
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage());
    }

    @Override
    public ResponseModel<D> update(D d) {
        E e = mapper.toEntity(d);
        Optional<E> eOptional = repo.findById(e.getId());
        if (!eOptional.isPresent()) {
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage(), 204);
        }
        E eSave = eOptional.get();
        repo.saveAndFlush(eSave);
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), d);
    }

    @Override
    public ResponseModel<List<D>> getAll(Integer pageNum, Integer pageSize, String field, boolean isDesc) {
        Pageable pageable;

        if (field == null || field.isEmpty()) {
            pageable = PageRequest.of(pageNum == null ? PageConstant.PAGE_DEFAULT.getNum() : pageNum,
                    pageSize == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : pageSize);
        } else {
            pageable = appUtils.getPageable(pageNum, pageSize, field, isDesc);
        }

        Page<E> ePage = repo.findAll(pageable);
        Page<D> dPage = ePage.map(mapper::toDTO);
        ResponsePageableModel<D> dResponsePageableModel = new ResponsePageableModel<>(dPage.getContent(), dPage.getPageable(), dPage.getTotalElements());
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), dResponsePageableModel);
    }

    @Override
    public ResponseModel<D> getById(ID id) {
        Optional<E> eOptional = repo.findById(id);
        if (!eOptional.isPresent()) {
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage(), 500);
        }
        D d = mapper.toDTO(eOptional.get());
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), d);
    }

    @Override
    public ResponseModel<String> deleteById(ID id) {
        Optional<E> eOptional = repo.findById(id);
        if (!eOptional.isPresent()) {
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage(), 204);
        }
        repo.deleteById(id);
        return ResponseModel.successful(String.format(ResponseMessage.SUCCESS.getMessage(), id));
    }

}
