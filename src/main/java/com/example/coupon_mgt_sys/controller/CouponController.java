package com.example.coupon_mgt_sys.controller;

import com.example.coupon_mgt_sys.entity.Coupon;
import com.example.coupon_mgt_sys.service.CouponService.CouponInfo;
import com.example.coupon_mgt_sys.service.CouponService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
@RequestMapping("/v1/coupons")
@Tag(name = "Coupon", description = "Coupon management APIs")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping
    @Operation(summary = "Store a new coupon")
    public ResponseEntity<String> storeCoupon(@RequestBody Coupon coupon) {
        String result = couponService.storeCoupon(coupon.getCouponCode(), coupon.getExpiryTime());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/claim")
    @Operation(summary = "To Claim a Coupon")
    public ResponseEntity<String> claimCoupon(@RequestBody ClaimRequest request) {
        String result = couponService.claimCoupon(request.getDeviceId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{couponCode}/status")
    @Operation(summary = "Get coupon status")
    public ResponseEntity<String> getCouponStatus(@PathVariable String couponCode) {
        String status = couponService.getCouponStatus(couponCode);
        return ResponseEntity.ok(status);
    }
    @GetMapping("/device/{deviceId}/status")
    @Operation(summary = "Get device status")
    public ResponseEntity<String> getDeviceStatus(@PathVariable String deviceId) {
        String status = couponService.getDeviceStatus(deviceId);
        return ResponseEntity.ok(status);
    }
    @GetMapping("/all")
    @Operation(summary = "Display all coupons")
    public ResponseEntity<List<CouponInfo>> getAllCouponsInfo() {
        List<CouponService.CouponInfo> couponsInfo = couponService.getAllCouponsInfo();
        return ResponseEntity.ok(couponsInfo);
    }

    static class ClaimRequest {
        private String deviceId;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }
}
