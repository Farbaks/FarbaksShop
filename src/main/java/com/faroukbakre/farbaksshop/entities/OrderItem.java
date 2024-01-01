package com.faroukbakre.farbaksshop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="orderItems")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne()
    @JoinColumn(name = "productId")
    private Product product;

    private int quantity;

    private float amount;

    private float total;
}
