package com.faroukbakre.farbaksshop.products;

import com.faroukbakre.farbaksshop.config.SecurityConfig;
import com.faroukbakre.farbaksshop.entities.Address;
import com.faroukbakre.farbaksshop.entities.Role;
import com.faroukbakre.farbaksshop.entities.User;
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
public class ProductControllerIntegrationTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    JwtUtil jwtUtil;

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

    @Test
    public void when_ProductDoesNotExistDuringCreation_Expect_Success() throws Exception {
        this.productRepository.deleteAll();

        this.saveTestUser();

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiYWRtaW4iXSwiaWF0IjoxNzA0NjIwNzY3LCJleHAiOjE3MDU0ODQ3Njd9.FibPwDo0ypeUHCU37Rempjb7FPWY9sMAy9P6Q6CMgxY";

        String json_userToCreate =
                "{\n  " +
                    "\"name\": \"Green Puffer Jacket\",\n  " +
                    "\"quantity\": 10,\n  \"amount\": 24.5,\n  " +
                    "\"description\": \"This is the description for this green puffer jacket\",\n  " +
                    "\"categoryId\": 1,\n  " +
                    "\"color\": \"Green\"\n" +
                "}";

        mockMvc
                .perform(post("/products")
                        .header("AUTHORIZATION", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_userToCreate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("Product creation successful."));
    }

    public void saveTestUser() {
        this.addressRepository.deleteAll();
        this.userRepository.deleteAll();

        Role role = this.roleRepository.findById(1).orElse(null);

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
