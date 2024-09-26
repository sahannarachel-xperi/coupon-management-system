package com.example.coupon_mgt_sys.service;

import com.example.coupon_mgt_sys.entity.Coupon;
import com.example.coupon_mgt_sys.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public String storeCoupon(String couponCode, String expiryTime) {
        Coupon existingCoupon = couponRepository.findByCouponCode(couponCode);
        if (existingCoupon != null) {
            return "Coupon code already exists.";
        }

        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponCode);
        coupon.setExpiryTime(expiryTime);
        couponRepository.save(coupon);
        return "Coupon stored successfully.";
    }

    public String claimCoupon(String deviceId) {
        // Check if the device has already claimed a coupon
        List<Coupon> allCoupons = couponRepository.findAll();
        for (Coupon coupon : allCoupons) {
            if (deviceId.equals(coupon.getDeviceId())) {
                return "This device has already claimed a coupon.";
            }
        }

        // Find an available coupon
        for (Coupon coupon : allCoupons) {
            if (coupon.getDeviceId() == null &&
                    Instant.parse(coupon.getExpiryTime()).isAfter(Instant.now())) {
                coupon.setDeviceId(deviceId);
                couponRepository.save(coupon);
                return String.format("Coupon claimed successfully. Coupon code: %s, Expiry time: %s",
                        coupon.getCouponCode(), coupon.getExpiryTime());
            }
        }

        return "No available coupons to claim.";
    }

    public String getCouponStatus(String couponCode) {
        Coupon coupon = couponRepository.findByCouponCode(couponCode);
        if (coupon == null) {
            return "Coupon does not exist.";
        }

        if (coupon.getDeviceId() != null) {
            return "Coupon has already been claimed.";
        }

        if (Instant.parse(coupon.getExpiryTime()).isBefore(Instant.now())) {
            return "Coupon has expired.";
        }

        return "Coupon is available for claiming.";
    }

    public String getDeviceStatus(String deviceId) {
        List<Coupon> allCoupons = couponRepository.findAll();
        Optional<Coupon> claimedCoupon = allCoupons.stream()
                .filter(coupon -> deviceId.equals(coupon.getDeviceId()))
                .findFirst();

        if (claimedCoupon.isPresent()) {
            Coupon coupon = claimedCoupon.get();
            return String.format("Device %s has claimed coupon %s. Expiry time: %s",
                    deviceId, coupon.getCouponCode(), coupon.getExpiryTime());
        } else {
            return String.format("Device %s has not claimed any coupon.", deviceId);
        }
    }

    public List<CouponInfo> getAllCouponsInfo() {
        List<Coupon> allCoupons = couponRepository.findAll();
        return allCoupons.stream()
                .map(this::convertToCouponInfo)
                .collect(Collectors.toList());
    }

    private CouponInfo convertToCouponInfo(Coupon coupon) {
        boolean isClaimed = coupon.getDeviceId() != null;
        boolean isExpired = Instant.parse(coupon.getExpiryTime()).isBefore(Instant.now());
        return new CouponInfo(
                coupon.getCouponCode(),
                coupon.getDeviceId(),
                isClaimed,
                isExpired,
                coupon.getExpiryTime()
        );
    }
    @Setter
    @Getter
    public static class CouponInfo {
        @Setter
        @Getter
        private String couponCode;
        private String deviceId;
        private boolean claimed;
        private boolean expired;
        private String expiryDate;

        // Constructor
        public CouponInfo(String couponCode, String deviceId, boolean claimed, boolean expired, String expiryDate) {
            this.couponCode = couponCode;
            this.deviceId = deviceId;
            this.claimed = claimed;
            this.expired = expired;
            this.expiryDate = expiryDate;
        }

    }
}
