package com.progect.order.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(name = "is_delivery", nullable = false)
    private Boolean isDelivery = false;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "cutlery")
    private String cutlery;

    @Column(name = "payment_kind")
    private String paymentKind;

    @Column(name = "is_table_order", nullable = false)
    private Boolean isTableOrder = false;

    @Lob
    @Column(name = "notes")
    private String notes;

    @ElementCollection
    @Column(name = "dish_id")
    @CollectionTable(name = "order_dishes", joinColumns = @JoinColumn(name = "order_id"))
    private List<Long> dishes = new ArrayList<>();

    public Order(String customerName, String customerPhone, String customerEmail,
                 Boolean isDelivery, String deliveryAddress, LocalDateTime orderDate,
                 String cutlery, String paymentKind, Boolean isTableOrder,
                 String notes, List<Long> dishes) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.isDelivery = isDelivery;
        this.deliveryAddress = deliveryAddress;
        this.orderDate = orderDate;
        this.cutlery = cutlery;
        this.paymentKind = paymentKind;
        this.isTableOrder = isTableOrder;
        this.notes = notes;
        this.dishes = dishes;
    }
}
