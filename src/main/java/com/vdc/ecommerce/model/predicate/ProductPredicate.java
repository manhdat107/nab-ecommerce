package com.vdc.ecommerce.model.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.vdc.ecommerce.model.Color;
import com.vdc.ecommerce.model.MetricSearch;
import com.vdc.ecommerce.model.QProduct;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;


@Component
public class ProductPredicate {

    public static final QProduct Q_PRODUCT = QProduct.product;

    public Predicate findByMetricFilter(MetricSearch metricSearch) {
        BooleanExpression bExpression = Expressions.asBoolean(true).isTrue();
        if (!CollectionUtils.isEmpty(metricSearch.getMetricFilters())) {
            for (MetricSearch.MetricFilter o : metricSearch.getMetricFilters()) {
                if ("name".equals(o.getField())) {
                    bExpression = andNameLike(bExpression, o.getValue());
                }
                if ("color".equals(o.getField())) {
                    bExpression = andColorEqual(bExpression, Color.lookup(o.getValue()));
                }
                if ("price".equals(o.getField())) {
                    BigDecimal price = o.getValue() == null || o.getValue().isEmpty() ? BigDecimal.ZERO : new BigDecimal(o.getValue());
                    if (o.getCondition().contains(">")) {
                        bExpression = andPriceMoreThanOrEqual(bExpression, price);
                    } else if (o.getCondition().contains("<")) {
                        bExpression = andPriceLessThan(bExpression, price);
                    } else {
                        bExpression = andPriceEqual(bExpression, price);
                    }
                }
                if ("branch".equals(o.getField())) {
                    bExpression = andBranchLike(bExpression, o.getValue());
                }
                if ("quantity".equals(o.getField())) {
                    Long quantity = o.getValue() == null || o.getValue().isEmpty() ? 0L : Long.parseLong(o.getValue());
                    if (o.getCondition().contains(">")) {
                        bExpression = andQuantityMoreThanOrEqual(bExpression, quantity);
                    } else if (o.getCondition().contains("<")) {
                        bExpression = andQuantityLessThanOrEqual(bExpression, quantity);
                    } else {
                        bExpression = andQuantityEqual(bExpression, quantity);
                    }
                }
            }
        }
        return bExpression;
    }

    public Predicate findByProductIdIn(List<Long> ids) {
        if (ids.isEmpty()) {
            return null;
        }
        return Q_PRODUCT.id.in(ids);
    }

    public BooleanExpression andNameEqual(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.eq(value));
    }

    public BooleanExpression andNameLike(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.like("%" + value + "%"));
    }

    public BooleanExpression andColorEqual(BooleanExpression predicate, Color value) {
        return predicate.and(Q_PRODUCT.color.eq(value));
    }

    public BooleanExpression andPriceEqual(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.eq(value));
    }

    public BooleanExpression andPriceLessThan(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.lt(value));
    }

    public BooleanExpression andPriceLessThanOrEqual(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.loe(value));
    }

    public BooleanExpression andPriceMoreThan(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.gt(value));
    }

    public BooleanExpression andPriceMoreThanOrEqual(BooleanExpression predicate, BigDecimal value) {
        return predicate.and(Q_PRODUCT.price.goe(value));
    }

    public BooleanExpression andBranchEqual(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.eq(value));
    }

    public BooleanExpression andBranchLike(BooleanExpression predicate, String value) {
        return predicate.and(Q_PRODUCT.name.like("%" + value + "%"));
    }

    public BooleanExpression andQuantityEqual(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.eq(value));
    }

    public BooleanExpression andQuantityLessThan(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.lt(value));
    }

    public BooleanExpression andQuantityLessThanOrEqual(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.loe(value));
    }

    public BooleanExpression andQuantityMoreThan(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.gt(value));
    }

    public BooleanExpression andQuantityMoreThanOrEqual(BooleanExpression predicate, Long value) {
        return predicate.and(Q_PRODUCT.quantity.quantity.goe(value));
    }


}
