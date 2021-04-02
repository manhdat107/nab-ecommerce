package com.vdc.ecommerce.reposirtory;

import com.vdc.ecommerce.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Long>, QuerydslPredicateExecutor<OrderDetail> {
}
