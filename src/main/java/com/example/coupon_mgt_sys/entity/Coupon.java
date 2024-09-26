package com.example.coupon_mgt_sys.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@DynamoDBTable(tableName = "Coupons")
public class Coupon {

    @DynamoDBHashKey
    @DynamoDBAttribute
    private String couponCode;

    @DynamoDBAttribute
    private String expiryTime;

    @DynamoDBAttribute
    private String deviceId;

    // Getters and setters

}