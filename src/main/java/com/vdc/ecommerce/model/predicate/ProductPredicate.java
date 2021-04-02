package com.vdc.ecommerce.model.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.vdc.ecommerce.model.Color;
import com.vdc.ecommerce.model.QProduct;

import java.math.BigDecimal;

public class ProductPredicate {
    public static final QProduct Q_PRODUCT = QProduct.product;

    public BooleanExpression andNameEqual(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.eq(value));
    }

    public static BooleanExpression andNameLike(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.like("%" + value + "%"));
    }

    public static BooleanExpression andColorEqual(BooleanExpression predicate, Color value) {
        return predicate.and(Q_PRODUCT.color.eq(value));
    }

    public static BooleanExpression andPriceEqual(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.eq(value));
    }

    public static BooleanExpression andPriceLessThan(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.lt(value));
    }

    public static BooleanExpression andPriceLessThanOrEqual(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.loe(value));
    }

    public static BooleanExpression andPriceMoreThan(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.gt(value));
    }

    public static BooleanExpression andPriceMoreThanOrEqual(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.goe(value));
    }

    public static BooleanExpression andBranchEqual(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.eq(value));
    }

    public static BooleanExpression andBranchLike(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.like("%" + value + "%"));
    }

    public static BooleanExpression andQuantityEqual(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.eq(value));
    }

    public static BooleanExpression andQuantityLessThan(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.lt(value));
    }

    public static BooleanExpression andQuantityLessThanOrEqual(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.loe(value));
    }

    public static BooleanExpression andQuantityMoreThan(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.gt(value));
    }

    public static BooleanExpression andQuantityMoreThanOrEqual(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.goe(value));
    }

}
