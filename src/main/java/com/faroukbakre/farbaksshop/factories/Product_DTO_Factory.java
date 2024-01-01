package com.faroukbakre.farbaksshop.factories;

import com.faroukbakre.farbaksshop.dto.DefaultResponseDTO;
import com.faroukbakre.farbaksshop.dto.ProductResponseDTO;
import com.faroukbakre.farbaksshop.entities.Product;
import com.faroukbakre.farbaksshop.entities.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Product_DTO_Factory {

    public DefaultResponseDTO createProductResponseDTO(Product product, String message)
    {
        ProductResponseDTO productDTO = new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getDescription(),
                product.getAmount(),
                product.getColor(),
                product.getCategory()
        );

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatusCode(200);
        response.setMessage(message);
        response.setData(product);

        return response;
    }

    public DefaultResponseDTO createProductListResponseDTO(List<ProductResponseDTO> data) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatusCode(200);
        response.setMessage("Products fetched successfully.");
        response.setData(data);

        return response;
    }

    public ProductResponseDTO createProductDto(Product product)
    {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getQuantity(),
                product.getDescription(),
                product.getAmount(),
                product.getColor(),
                product.getCategory()
        );
    }
}
