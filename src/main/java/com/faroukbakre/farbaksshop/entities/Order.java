package com.faroukbakre.farbaksshop.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "userId")
    private User user;

    private int quantity;

    private float amount;

    private String status;

    @ManyToOne()
    @JoinColumn(name = "driverId")
    private User driver;

    @OneToMany(mappedBy="order")
    @OrderBy(value="id")
    private List<OrderItem> items = new ArrayList<>();
}
