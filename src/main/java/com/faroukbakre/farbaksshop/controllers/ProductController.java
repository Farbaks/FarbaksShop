package com.faroukbakre.farbaksshop.controllers;

import com.faroukbakre.farbaksshop.dto.responses.DefaultResponseDTO;
import com.faroukbakre.farbaksshop.dto.requests.EditProductRequestDTO;
import com.faroukbakre.farbaksshop.dto.requests.NewProductRequestDTO;
import com.faroukbakre.farbaksshop.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping( "/products")
@AllArgsConstructor
@Validated
public class ProductController {

    private  final ProductService productService;

    @PostMapping()
    public DefaultResponseDTO createProduct(@Valid @RequestBody NewProductRequestDTO data) {
        return this.productService.createProduct(data);
    }

    @GetMapping()
    public DefaultResponseDTO getAllProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("{id}")
    public DefaultResponseDTO getOneProduct(
            @Min(value = 1, message = "Id must be greater than zero")
            @PathVariable(name = "id") int id) {
        return this.productService.getOneProduct(id);
    }

    @PutMapping("{id}")
    public DefaultResponseDTO updateProduct(
            @Min(value = 1, message = "Id must be greater than zero")
            @PathVariable(name = "id") int id, @Valid @RequestBody EditProductRequestDTO data) {
        return this.productService.editProduct(id, data);
    }

    @DeleteMapping("{id}")
    public DefaultResponseDTO deleteProduct(
            @Min(value = 1, message = "Id must be greater than zero")
            @PathVariable(name = "id") int id) {
        return this.productService.deleteProduct(id);
    }
}
