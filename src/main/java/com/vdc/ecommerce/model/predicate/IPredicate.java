package com.vdc.ecommerce.model.predicate;

import com.querydsl.core.types.Predicate;
import com.vdc.ecommerce.model.MetricSearch;

public interface IPredicate {
    Predicate findByMetricFilter(MetricSearch metricSearch);
}
