package com.vdc.ecommerce.service.impl;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.common.AppUtils;
import com.vdc.ecommerce.common.PageConstant;
import com.vdc.ecommerce.common.ResponseMessage;
import com.vdc.ecommerce.model.BaseEntity;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.dto.BaseDTO;
import com.vdc.ecommerce.model.mapper.BaseMapper;
import com.vdc.ecommerce.model.response.ResponseModel;
import com.vdc.ecommerce.model.response.ResponsePageableModel;
import com.vdc.ecommerce.service.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

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
    protected QuerydslPredicateExecutor<E> queryDsl;
    protected BaseMapper<E, D> mapper;

    public BaseServiceImpl(JpaRepository<E, ID> repo, BaseMapper<E, D> mapper, QuerydslPredicateExecutor<E> queryDsl) {
        this.repo = repo;
        this.mapper = mapper;
        this.queryDsl = queryDsl;
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
            pageable = AppUtils.getPageable(pageNum, pageSize, field, isDesc);
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
            return ResponseModel.failure(ResponseMessage.NOT_FOUND.getMessage(), 404);
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

    @Override
    public ResponseModel<List<D>> findByPredicate(MetricSearch metricSearch, Predicate predicate) {
        Pageable pageable;
        int page = 0;
        int pageSize = 0;
        String sortField = null;
        Boolean isDesc = null;
        if (metricSearch != null) {
            if (metricSearch.getPage() != null) {
                page = metricSearch.getPage();
            } else {
                page = PageConstant.PAGE_DEFAULT.getNum();
            }
            if (metricSearch.getPageSize() != null && metricSearch.getPageSize() != 0) {
                pageSize = metricSearch.getPageSize();
            } else {
                pageSize = PageConstant.PAGE_SIZE_DEFAULT.getNum();
            }
            if (metricSearch.getField() != null && !metricSearch.getField().isEmpty()) {
                sortField = metricSearch.getField();
                isDesc = metricSearch.isDest();
            }
        }
        pageable = AppUtils.getPageable(page, pageSize, sortField, isDesc);

        Page<E> pResult = queryDsl.findAll(predicate, pageable);

        List<D> rsDTOs = mapper.toDTOs(pResult.getContent());
        ResponsePageableModel<D> dResponsePageableModel = new ResponsePageableModel<D>(rsDTOs, pResult.getPageable(), pResult.getTotalElements());
        return ResponseModel.successful(ResponseMessage.SUCCESS.getMessage(), dResponsePageableModel);
    }

    private E createContents(Class<E> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
