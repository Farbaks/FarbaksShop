package com.faroukbakre.farbaksshop.orders;

import com.faroukbakre.farbaksshop.config.SecurityConfig;
import com.faroukbakre.farbaksshop.entities.*;
import com.faroukbakre.farbaksshop.models.entities.*;
import com.faroukbakre.farbaksshop.models.repositories.*;
import com.faroukbakre.farbaksshop.repositories.*;
import com.faroukbakre.farbaksshop.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "com.faroukbakre.farbaksshop")
@AutoConfigureMockMvc
public class OrderControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    RoleRepository roleRepository;

    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiY3VzdG9tZXIiXSwiaWF0IjoxNzA0ODMzMDk2LCJleHAiOjE3MDU2OTcwOTZ9.A5V7xETxc1dk_oZJ071NBANWIsYjB6ARd4Hfxl_pd8Y";

    @Test
    public void when_CustomerOrdersProduct_Expect_Success () throws Exception {
        this.saveTestUser();
        Category category = this.categoryRepository.findById(1).orElse(null);
        Product newProduct = new Product();
        newProduct.setName("Green Puffer Jacket");
        newProduct.setQuantity(4);
        newProduct.setCategory(category);
        newProduct.setAmount(100);
        newProduct.setColor("Red");
        newProduct.setDescription("This is the description");

        this.productRepository.save(newProduct);

        String json_orderToCreate =
                "{\n  " +
                    "\"orders\": [\n    " +
                        "{\n      " +
                            "\"productId\": 1,\n      " +
                            "\"quantity\": 3\n    " +
                        "}\n  " +
                    "]\n" +
                "}";

        mockMvc
                .perform(post("/orders")
                        .header("AUTHORIZATION", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_orderToCreate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("Order created successfully."));

    }

    public void saveTestUser() {
        this.addressRepository.deleteAll();
        this.userRepository.deleteAll();

        Role role = this.roleRepository.findById(3).orElse(null);

        User newUser = new User();
        newUser.setFirstName("Farouk");
        newUser.setLastName("Bakre");
        newUser.setEmailAddress("faroukbakre@yahoo.com");
        newUser.setPhoneNumber("+2348145251688");
        String hashedPassword = passwordEncoder.encode("0000");
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);
        this.userRepository.save(newUser);

        // Save Address
        Address newAddress = new Address();
        newAddress.setAddressLine("High Road ,Meadow-under-Hill, F12 6GX");
        newAddress.setUser(newUser);
        this.addressRepository.save(newAddress);
    }
}
