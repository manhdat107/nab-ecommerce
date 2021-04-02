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
public class ProductPredicate implements IPredicate{

    public static final QProduct Q_PRODUCT = QProduct.product;

    @Override
    public Predicate findByMetricFilter(MetricSearch metricSearch) {
        BooleanExpression bExpression = Expressions.asBoolean(true).isTrue();
        if (!CollectionUtils.isEmpty(metricSearch.getMetricFilters())) {
            for (MetricSearch.MetricFilter o : metricSearch.getMetricFilters()) {
                if ("name".equals(o.getField())) {
                    bExpression = ProductExpression.andNameLike(bExpression, o.getValue());
                }
                if ("color".equals(o.getField())) {
                    bExpression = ProductExpression.andColorEqual(bExpression, Color.lookup(o.getValue()));
                }
                if ("price".equals(o.getField())) {
                    BigDecimal price = o.getValue() == null || o.getValue().isEmpty() ? BigDecimal.ZERO : new BigDecimal(o.getValue());
                    if (o.getCondition().contains(">")) {
                        bExpression = ProductExpression.andPriceMoreThanOrEqual(bExpression, price);
                    } else if (o.getCondition().contains("<")) {
                        bExpression = ProductExpression.andPriceLessThan(bExpression, price);
                    } else {
                        bExpression = ProductExpression.andPriceEqual(bExpression, price);
                    }
                }
                if ("branch".equals(o.getField())) {
                    bExpression = ProductExpression.andBranchLike(bExpression, o.getValue());
                }
                if ("quantity".equals(o.getField())) {
                    Long quantity = o.getValue() == null || o.getValue().isEmpty() ? 0L : Long.parseLong(o.getValue());
                    if (o.getCondition().contains(">")) {
                        bExpression = ProductExpression.andQuantityMoreThanOrEqual(bExpression, quantity);
                    } else if (o.getCondition().contains("<")) {
                        bExpression = ProductExpression.andQuantityLessThanOrEqual(bExpression, quantity);
                    } else {
                        bExpression = ProductExpression.andQuantityEqual(bExpression, quantity);
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
}
