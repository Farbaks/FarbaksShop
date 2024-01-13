package com.faroukbakre.farbaksshop.dto.factories;

import com.faroukbakre.farbaksshop.dto.responses.DefaultResponseDTO;
import com.faroukbakre.farbaksshop.dto.responses.ProductResponseDTO;
import com.faroukbakre.farbaksshop.models.entities.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Product_DTO_Factory {

    public DefaultResponseDTO createProductResponseDTO(Product product, String message)
    {

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
