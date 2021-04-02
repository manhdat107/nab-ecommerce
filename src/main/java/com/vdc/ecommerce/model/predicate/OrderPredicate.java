package com.vdc.ecommerce.model.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.vdc.ecommerce.model.MetricSearch;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;

@Component
public class OrderPredicate implements IPredicate {
    @Override
    public Predicate findByMetricFilter(MetricSearch metricSearch) {
        BooleanExpression bExpression = Expressions.asBoolean(true).isTrue();
        if (!CollectionUtils.isEmpty(metricSearch.getMetricFilters())) {
            for (MetricSearch.MetricFilter o : metricSearch.getMetricFilters()) {
                if ("fullname".equals(o.getField())) {
                    bExpression = OrderExpression.andFullnameLike(bExpression, o.getValue());
                }

                if ("address".equals(o.getField())) {
                    bExpression = OrderExpression.andAddressLike(bExpression, o.getValue());
                }

                if ("phoneNumber".equals(o.getField())) {
                    Long phone = o.getValue() == null || o.getValue().isEmpty() ? 0L : Long.parseLong(o.getValue());
                    bExpression = OrderExpression.andPhoneNumberLike(bExpression, phone);
                }
                if ("email".equals(o.getField())) {
                    bExpression = OrderExpression.andEmailLike(bExpression, o.getValue());
                }

                if ("productName".equals(o.getField())) {
                    bExpression = OrderExpression.andProductNameLike(bExpression, o.getValue());
                }

                if ("totalPrice".equals(o.getField())) {
                    BigDecimal price = o.getValue() == null || o.getValue().isEmpty() ? BigDecimal.ZERO : new BigDecimal(o.getValue());
                    if (o.getCondition().contains(">")) {
                        bExpression = OrderExpression.andTotalPriceMoreThanOrEqual(bExpression, price);
                    } else if (o.getCondition().contains("<")) {
                        bExpression = OrderExpression.andTotalPriceLessThan(bExpression, price);
                    } else {
                        bExpression = OrderExpression.andTotalPriceEqual(bExpression, price);
                    }
                }
            }
        }
        return bExpression;
    }
}
