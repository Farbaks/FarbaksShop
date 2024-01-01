package com.faroukbakre.farbaksshop.services;

import com.faroukbakre.farbaksshop.dto.*;
import com.faroukbakre.farbaksshop.entities.Category;
import com.faroukbakre.farbaksshop.entities.Product;
import com.faroukbakre.farbaksshop.factories.Product_DTO_Factory;
import com.faroukbakre.farbaksshop.repositories.CategoryRepository;
import com.faroukbakre.farbaksshop.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

    private final Product_DTO_Factory productMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DefaultResponseDTO createProduct(NewProductRequestDTO data) {
        Product checkProduct = this.productRepository.findByName(data.getName());

        if(checkProduct != null) {
            return new DefaultResponseDTO(401, "Product with name already exists.");
        }

        Product newProduct = new Product();

        newProduct.setName(data.getName());
        newProduct.setQuantity(data.getQuantity());
        newProduct.setAmount(data.getAmount());
        newProduct.setDescription(data.getDescription());
        newProduct.setColor(data.getColor());

        // Set Category
        Category category = this.categoryRepository.findById(data.getCategoryId()).orElse(null);
        if (category == null) {
            return new DefaultResponseDTO(401, "Category does not exist.");
        }

        newProduct.setCategory(category);

        this.productRepository.save(newProduct);

        return this.productMapper.createProductResponseDTO(newProduct, "Product creation successful.");
    }

    public DefaultResponseDTO getAllProducts() {
        List<ProductResponseDTO> list = new ArrayList<>();
        List<Product> products = this.productRepository.findAll();

        for (Product product : products)
        {
            ProductResponseDTO productDto = this.productMapper.createProductDto(product);
            list.add(productDto);
        }

        return this.productMapper.createProductListResponseDTO(list);
    }

    public DefaultResponseDTO getOneProduct(int productId) {

        Product product = this.productRepository.findById(productId).orElse(null);

        if(product == null) {
            return new DefaultResponseDTO(401, "Product does not exist.");
        }

        return this.productMapper.createProductResponseDTO(product, "Product details fetched successful.");
    }

    public DefaultResponseDTO editProduct(int productId, EditProductRequestDTO updateProduct) {

        // Check if product id exists
        Product product = this.productRepository.findById(productId).orElse(null);

        if(product == null) {
            return new DefaultResponseDTO(401, "Product does not exist.");
        }

        // Check if name is already in user
        Product checkProduct = this.productRepository.findByName(updateProduct.getName());

        if(checkProduct != null && checkProduct.getId() != productId) {
            return new DefaultResponseDTO(401, "Product with name already exists.");
        }

        product.setName(updateProduct.getName());
        product.setQuantity(updateProduct.getQuantity());
        product.setAmount(updateProduct.getAmount());
        product.setDescription(updateProduct.getDescription());
        product.setColor(updateProduct.getColor());

        // Set Category
        Category category = this.categoryRepository.findById(updateProduct.getCategoryId()).orElse(null);
        if (category == null) {
            return new DefaultResponseDTO(401, "Category does not exist.");
        }

        product.setCategory(category);

        this.productRepository.save(product);

        return this.productMapper.createProductResponseDTO(product, "Product updated successfully.");

    }

    public DefaultResponseDTO deleteProduct(int productId) {

        Product product = this.productRepository.findById(productId).orElse(null);

        if(product == null) {
            return new DefaultResponseDTO(401, "Product does not exist.");
        }

        this.productRepository.deleteById(productId);

        return new DefaultResponseDTO(200, "Product deleted successfully.");
    }


}
