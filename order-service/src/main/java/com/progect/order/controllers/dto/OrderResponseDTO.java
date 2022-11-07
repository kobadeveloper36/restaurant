package com.progect.order.controllers.dto;

import com.progect.order.entities.Order;
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

    public OrderResponseDTO(Order order) {
        this.orderId = order.getOrderId();
        this.customerName = order.getCustomerName();
        this.customerPhone = order.getCustomerPhone();
        this.customerEmail = order.getCustomerEmail();
        this.isDelivery = order.getIsDelivery();
        this.deliveryAddress = order.getDeliveryAddress();
        this.orderDate = order.getOrderDate();
        this.cutlery = order.getCutlery();
        this.paymentKind = order.getPaymentKind();
        this.isTableOrder = order.getIsTableOrder();
        this.notes = order.getNotes();
        this.sum = order.getSum();
        this.dishes = order.getDishes();
    }
}
