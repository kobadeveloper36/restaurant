package com.progect.ui.rest.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
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
    private List<Long> dishes;
    private Long userId;
    private Double sum;
}
