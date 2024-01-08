package com.faroukbakre.farbaksshop.services;

import com.faroukbakre.farbaksshop.dto.*;
import com.faroukbakre.farbaksshop.entities.*;
import com.faroukbakre.farbaksshop.exceptions.CustomException;
import com.faroukbakre.farbaksshop.factories.Order_DTO_Factory;
import com.faroukbakre.farbaksshop.factories.Product_DTO_Factory;
import com.faroukbakre.farbaksshop.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class OrderService {

    private final Order_DTO_Factory orderMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public DefaultResponseDTO createOrder(HttpServletRequest request, NewOrderRequestDTO data) {

        String userId = (String) request.getAttribute("userId");
        User user = this.userRepository.findById(Integer.valueOf(userId)).orElse(null);
        if(user == null) throw new CustomException("Account does not exist.", 400, HttpStatus.OK);

        float totalAmount = 0;
        List<Map<String, Object>> orderItems = new ArrayList<>();

        // Check if products exist and are in stock
        for (NewOrderItemsRequestDTO order : data.getOrders())
        {
            Product product = this.productRepository.findByIdAndQuantityGreaterThanEqual(order.getProductId(), order.getQuantity());
            if(product == null) throw new CustomException("One or more products are not in stock.", 400, HttpStatus.OK);

            Map<String, Object> item = new HashMap<>();

            // For each product get the amount and calculate the sub-total
            item.put("product", product);
            item.put("quantity", order.getQuantity());
            item.put("amount", product.getAmount());

            float subAmount =  order.getQuantity() * product.getAmount();
            item.put("subAmount", subAmount);
            orderItems.add(item);

            totalAmount += subAmount;
        }


        // Insert into order table and Initialize status to 'processing'
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setDriver(null);
        newOrder.setQuantity(data.getOrders().size());
        newOrder.setAmount(totalAmount);
        newOrder.setStatus("processing");
        this.orderRepository.save(newOrder);

        // Insert order items
        List<OrderItem> saveItems = new ArrayList<>();

        for ( Map<String, Object> item : orderItems)
        {
            OrderItem newItem = new OrderItem();

            newItem.setOrder(newOrder);
            newItem.setProduct((Product) item.get("product"));
            newItem.setQuantity((Integer) item.get("quantity"));
            newItem.setAmount((float) item.get("amount"));
            newItem.setTotal((float) item.get("subAmount"));
            saveItems.add(newItem);
        }
        this.orderItemRepository.saveAll(saveItems);
        newOrder.setItems(saveItems);

        // Update product quantities
        for (NewOrderItemsRequestDTO orderItem : data.getOrders())
        {
            Product product = this.productRepository.findById(orderItem.getProductId()).orElse(null);
            if(product == null) throw new CustomException("An error occurred.", 400, HttpStatus.OK);
            int newQuantity = product.getQuantity() - orderItem.getQuantity();
            product.setQuantity(newQuantity);
            this.productRepository.save(product);
        }

        return this.orderMapper.createOrderResponseDTO(newOrder, "Order created successfully.");

    }

    public DefaultResponseDTO getMyOrders(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        List<OrderResponseDTO> list = new ArrayList<>();
        List<Order> orders = this.orderRepository.findAllByUserId(Integer.parseInt(userId));

        for (Order order : orders)
        {
            OrderResponseDTO orderDto = this.orderMapper.createOrderDto(order);
            list.add(orderDto);
        }

        return this.orderMapper.createOrderListResponseDTO(list);
    }

    public DefaultResponseDTO getDriverOrders(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        List<OrderResponseDTO> list = new ArrayList<>();
        List<Order> orders = this.orderRepository.findAllByDriverId(Integer.parseInt(userId));

        for (Order order : orders)
        {
            OrderResponseDTO orderDto = this.orderMapper.createOrderDto(order);
            list.add(orderDto);
        }

        return this.orderMapper.createOrderListResponseDTO(list);
    }

    public DefaultResponseDTO getAllOrders() {

        List<OrderResponseDTO> list = new ArrayList<>();
        List<Order> orders = this.orderRepository.findAll();

        for (Order order : orders)
        {
            OrderResponseDTO orderDto = this.orderMapper.createOrderDto(order);
            list.add(orderDto);
        }

        return this.orderMapper.createOrderListResponseDTO(list);
    }

    public DefaultResponseDTO getOneOrder(int orderId) {

        Order order = this.orderRepository.findById(orderId).orElse(null);
        if(order == null) throw new CustomException("Order does not exist.", 400, HttpStatus.OK);

        return this.orderMapper.createOrderResponseDTO(order, "Order details fetched successful.");
    }

    public DefaultResponseDTO assignOrderToDriver(int orderId, AssignOrderRequestDTO data) {

        Order order = this.orderRepository.findById(orderId).orElse(null);
        if(order == null) throw new CustomException("Order does not exist.", 400, HttpStatus.OK);

        User driver = this.userRepository.findByIdAndRoleId(data.getDriverId(), 2);
        if(driver == null) throw new CustomException("Driver does not exist.", 400, HttpStatus.OK);

        order.setDriver(driver);
        order.setStatus("shipped");
        this.orderRepository.save(order);

        return this.orderMapper.createOrderResponseDTO(order, "Order assigned successfully.");
    }

    public DefaultResponseDTO updateOrderStatus(int orderId, EditOrderRequestDTO data) {

        Order order = this.orderRepository.findById(orderId).orElse(null);
        if(order == null) throw new CustomException("Order does not exist.", 400, HttpStatus.OK);

        order.setStatus(data.getStatus());
        this.orderRepository.save(order);

        return this.orderMapper.createOrderResponseDTO(order, "Order status updated successfully.");
    }
}
