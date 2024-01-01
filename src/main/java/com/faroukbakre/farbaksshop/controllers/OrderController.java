package com.faroukbakre.farbaksshop.controllers;

import com.faroukbakre.farbaksshop.dto.*;
import com.faroukbakre.farbaksshop.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping( "/orders")
@AllArgsConstructor
@Validated
public class OrderController {

    private  final OrderService orderService;

    @PostMapping()
    public DefaultResponseDTO createOrder(HttpServletRequest request, @Valid  @RequestBody NewOrderRequestDTO data) {
        return this.orderService.createOrder(request, data);
    }

    @GetMapping("")
    public DefaultResponseDTO getMyOrder(HttpServletRequest request) {
        return this.orderService.getMyOrders(request);
    }

    @GetMapping("/driver")
    public DefaultResponseDTO getDriverOrders(HttpServletRequest request) {
        return this.orderService.getDriverOrders(request);
    }

    @GetMapping("all")
    public DefaultResponseDTO getAllOrder() {
        return this.orderService.getAllOrders();
    }

    @GetMapping("{id}")
    public DefaultResponseDTO getOrderDetail(
            @Min(value = 1, message = "Id must be greater than zero")
            @PathVariable(name = "id") int id) {
        return this.orderService.getOneOrder(id);
    }

    @PutMapping("{id}/assign")
    public DefaultResponseDTO assignOrderToDriver(
            @Min(value = 1, message = "Id must be greater than zero")
            @PathVariable(name = "id") int id, @Valid @RequestBody AssignOrderRequestDTO data) {
        return this.orderService.assignOrderToDriver(id, data);
    }

    @PutMapping("{id}")
    public DefaultResponseDTO updateOrderStatus(
            @Min(value = 1, message = "Id must be greater than zero")
            @PathVariable(name = "id") int id, @Valid @RequestBody EditOrderRequestDTO data) {
        return this.orderService.updateOrderStatus(id, data);
    }
}
