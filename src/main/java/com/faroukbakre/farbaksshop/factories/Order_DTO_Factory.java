package com.faroukbakre.farbaksshop.factories;

import com.faroukbakre.farbaksshop.dto.*;
import com.faroukbakre.farbaksshop.entities.Address;
import com.faroukbakre.farbaksshop.entities.Order;
import com.faroukbakre.farbaksshop.entities.OrderItem;
import com.faroukbakre.farbaksshop.entities.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Order_DTO_Factory {

    public DefaultResponseDTO createOrderResponseDTO(Order order, String message)
    {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
                order.getId(),
                order.getAmount(),
                order.getQuantity(),
                order.getStatus()
        );

        orderResponseDTO.setItems(createDTOCollection(order.getItems()));

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatusCode(200);
        response.setMessage(message);

        response.setData(orderResponseDTO);

        return response;
    }

    public DefaultResponseDTO createOrderListResponseDTO(List<OrderResponseDTO> data) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatusCode(200);
        response.setMessage("Orders fetched successfully.");
        response.setData(data);

        return response;
    }

    public OrderResponseDTO createOrderDto(Order order)
    {
        return new OrderResponseDTO(
                order.getId(),
                order.getAmount(),
                order.getQuantity(),
                order.getStatus()
        );
    }

    private List<OrderItemResponseDTO> createDTOCollection(List<OrderItem> items)
    {
        return items.stream().map(this::createOrderItemDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    public OrderItemResponseDTO createOrderItemDTO(OrderItem item)
    {
        System.out.println(item.getId());
        return new OrderItemResponseDTO(
                item.getId(),
                item.getProduct().getId(),
                item.getQuantity(),
                item.getAmount(),
                item.getTotal()
        );
    }
}
