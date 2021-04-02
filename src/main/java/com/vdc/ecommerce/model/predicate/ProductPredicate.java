package com.vdc.ecommerce.model.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
                    bExpression = bExpression.and(Q_PRODUCT.name.like("%" + o.getValue() + "%"));
                }
                if ("color".equals(o.getField())) {
                    bExpression = bExpression.and(Q_PRODUCT.name.like("%" + o.getValue() + "%"));
                }
                if ("price".equals(o.getField())) {
                    BigDecimal price = o.getValue() == null || o.getValue().isEmpty() ? BigDecimal.ZERO : new BigDecimal(o.getValue());
                    if (o.getCondition().contains(">")) {
                        bExpression = bExpression.and(Q_PRODUCT.price.goe(price));
                    } else if (o.getCondition().contains("<")) {
                        bExpression = bExpression.and(Q_PRODUCT.price.loe(price));
                    } else {
                        bExpression = bExpression.and(Q_PRODUCT.price.eq(price));
                    }
                }
                if ("branch".equals(o.getField())) {
                    bExpression = bExpression.and(Q_PRODUCT.branch.name.like("%" + o.getValue() + "%"));
                }
                if ("quantity".equals(o.getField())) {
                    Long quantity = o.getValue() == null || o.getValue().isEmpty() ? 0L : Long.parseLong(o.getValue());
                    if (o.getCondition().contains(">")) {
                        bExpression = bExpression.and(Q_PRODUCT.quantity.quantity.goe(quantity));
                    } else if (o.getCondition().contains("<")) {
                        bExpression = bExpression.and(Q_PRODUCT.quantity.quantity.loe(quantity));
                    } else {
                        bExpression = bExpression.and(Q_PRODUCT.quantity.quantity.eq(quantity));
                    }
                }
            }
        }
        return bExpression;
    }

    public Predicate findByProductIdIn(List<Long> ids) {
        if(ids.isEmpty()) {
            return null;
        }
        return Q_PRODUCT.id.in(ids);
    }

}
