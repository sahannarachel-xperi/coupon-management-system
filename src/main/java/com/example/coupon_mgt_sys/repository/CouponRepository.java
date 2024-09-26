package com.example.coupon_mgt_sys.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.example.coupon_mgt_sys.entity.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouponRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void save(Coupon coupon) {
        dynamoDBMapper.save(coupon);
    }

    public Coupon findByCouponCode(String couponCode) {
        return dynamoDBMapper.load(Coupon.class, couponCode);
    }

    public List<Coupon> findAll() {
        return dynamoDBMapper.scan(Coupon.class, new DynamoDBScanExpression());
    }
}