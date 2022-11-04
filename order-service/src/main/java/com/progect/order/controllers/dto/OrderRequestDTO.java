package com.progect.order.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderRequestDTO {
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Boolean isDelivery;
    private String deliveryAddress;
    private String cutlery;
    private String paymentKind;
    private Boolean isTableOrder;
    private String notes;
    private List<Long> dishes;
}
