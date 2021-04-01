package com.vdc.ecommerce.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MetricSearch {
    List<MetricFilter> metricFilters;
    private Integer page;
    private Integer pageSize;
    private String field; // sort by field
    private boolean isDest;

    @Getter
    @Setter
    public static class MetricFilter {
        private String field;
        private String value;
        private String condition; // using for filter price
    }
}
