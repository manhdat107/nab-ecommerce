package com.vdc.ecommerce.service.impl;

import com.vdc.ecommerce.common.PageConstant;
import com.vdc.ecommerce.model.BaseEntity;
import com.vdc.ecommerce.model.dto.BaseDTO;
import com.vdc.ecommerce.model.mapper.BaseMapper;
import com.vdc.ecommerce.model.response.JsonResponse;
import com.vdc.ecommerce.model.response.RestResponsePage;
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

    public BaseServiceImpl(JpaRepository<E, ID> repo, BaseMapper<E, D> mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public JsonResponse<List<D>> create(List<D> dList) {
        List<E> eList = mapper.toEntities(dList);
        repo.saveAll(eList);
        return JsonResponse.successful("Create entities successful");
    }

    @Override
    public JsonResponse<D> add(D DTO) {
        E entity = mapper.toEntity(DTO);
        repo.save(entity);
        return JsonResponse.successful("Add successful");
    }

    @Override
    public JsonResponse<D> update(D d) {
        E e = mapper.toEntity(d);
        Optional<E> temp = repo.findById(e.getId());
        Optional<E> eOptional = repo.findById(e.getId());
        if (!eOptional.isPresent()) {
            return JsonResponse.failure("Not find object with this id to update", 200);
        }
        E eSave = eOptional.get();
        repo.saveAndFlush(eSave);
        return JsonResponse.successful("Update entities successful", d);
    }

    @Override
    public JsonResponse<List<D>> getAll(Integer pageNum, Integer pageSize) {
        pageNum = pageNum == null ? PageConstant.PAGE_NUM_DEFAULT.getNum() : pageNum;
        pageSize = pageSize == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : pageSize;
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        Page<E> ePage = repo.findAll(pageable);
        Page<D> dPage = ePage.map(mapper::toDTO);
        RestResponsePage<D> dRestResponsePage = new RestResponsePage<>(dPage.getContent(), dPage.getPageable(), dPage.getTotalElements());
        return JsonResponse.successful("Get all is success", dRestResponsePage);
    }

    @Override
    public JsonResponse<D> getById(ID id) {
        Optional<E> eOptional = repo.findById(id);
        if (!eOptional.isPresent()) {
            return JsonResponse.failure("Not find object with this id", 500);
        }
        D d = mapper.toDTO(eOptional.get());
        return JsonResponse.successful("Find it successful", d);
    }

    @Override
    public JsonResponse<String> deleteById(ID id) {
        Optional<E> eOptional = repo.findById(id);
        if (!eOptional.isPresent()) {
            return JsonResponse.failure("Not find object with this id", 204);
        }
        repo.deleteById(id);
        return JsonResponse.successful(String.format("Delete object successful with id %s", id));
    }

}
