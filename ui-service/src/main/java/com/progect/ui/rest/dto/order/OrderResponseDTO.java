package com.progect.ui.rest.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Boolean isDelivery;
    private String deliveryAddress;
    private LocalDateTime orderDate;
    private String cutlery;
    private String paymentKind;
    private Boolean isTableOrder;
    private String notes;
    private Double sum;
    private List<Long> dishes;
    private Long userId;
}
