package com.vdc.ecommerce.model.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.vdc.ecommerce.model.QOrderDetail;

import java.math.BigDecimal;

public class OrderExpression {

    private static final QOrderDetail ORDER_DETAIL = QOrderDetail.orderDetail;


    // full name expression
    public static BooleanExpression andFullnameEqual(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.fullname.eq(value));
    }

    public static BooleanExpression andFullnameLike(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.fullname.like(value));
    }

    //total price expression
    public static BooleanExpression andTotalPriceEqual(BooleanExpression expression, BigDecimal value){
        return expression.and(ORDER_DETAIL.totalPrice.eq(value));
    }

    public static BooleanExpression andTotalPriceLessThan(BooleanExpression expression, BigDecimal value){
        return expression.and(ORDER_DETAIL.totalPrice.lt(value));
    }

    public static BooleanExpression andTotalPriceLessThanOrEqual(BooleanExpression expression, BigDecimal value){
        return expression.and(ORDER_DETAIL.totalPrice.loe(value));
    }

    public static BooleanExpression andTotalPriceMoreThan(BooleanExpression expression, BigDecimal value){
        return expression.and(ORDER_DETAIL.totalPrice.gt(value));
    }

    public static BooleanExpression andTotalPriceMoreThanOrEqual(BooleanExpression expression, BigDecimal value){
        return expression.and(ORDER_DETAIL.totalPrice.goe(value));
    }

    //address expression
    public static BooleanExpression andAddressEqual(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.address.eq(value));
    }

    public static BooleanExpression andAddressLike(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.address.like(value));
    }

    //phone number expression
    public static BooleanExpression andPhoneNumberEqual(BooleanExpression expression, Long value) {
        return expression.and(ORDER_DETAIL.phoneNumber.eq(value));
    }

    public static BooleanExpression andPhoneNumberLike(BooleanExpression expression, Long value) {
        return expression.and(ORDER_DETAIL.phoneNumber.like(String.valueOf(value)));
    }

    //email expression
    public static BooleanExpression andEmailEqual(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.email.eq(value));
    }

    public static BooleanExpression andEmailLike(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.email.like(value));
    }

    //product name expression
    public static BooleanExpression andProductNameEqual(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.products.any().name.eq(value));
    }

    public static BooleanExpression andProductNameLike(BooleanExpression expression, String value) {
        return expression.and(ORDER_DETAIL.products.any().name.like(value));
    }
}
