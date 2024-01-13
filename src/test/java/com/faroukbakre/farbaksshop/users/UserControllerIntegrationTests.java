package com.faroukbakre.farbaksshop.users;

import com.faroukbakre.farbaksshop.config.SecurityConfig;
import com.faroukbakre.farbaksshop.models.entities.Address;
import com.faroukbakre.farbaksshop.models.entities.Role;
import com.faroukbakre.farbaksshop.models.entities.User;
import com.faroukbakre.farbaksshop.models.repositories.AddressRepository;
import com.faroukbakre.farbaksshop.models.repositories.RoleRepository;
import com.faroukbakre.farbaksshop.models.repositories.UserRepository;
import com.faroukbakre.farbaksshop.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "com.faroukbakre.farbaksshop")
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    RoleRepository roleRepository;

    @Test
    public void when_UserDoesNotExist_Expect_Success() throws Exception {
        this.addressRepository.deleteAll();
        this.userRepository.deleteAll();

        String json_userToCreate =
                "{\n  " +
                    "\"firstName\": \"Farbaks Driver\",\n  " +
                    "\"lastName\": \"Bakre\",\n  " +
                    "\"emailAddress\": \"faroukbakre@gmail.com\",\n  " +
                    "\"phoneNumber\": \"+447549404730\",\n  " +
                    "\"roleId\": 2,\n  " +
                    "\"password\": \"0000\",\n  " +
                    "\"address\": \"United Kingdom\"\n" +
                    "}";

        mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_userToCreate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("User signup successful."));

    }

    @Test
    public void when_UserWithEmailExists_Expect_ErrorMessage() throws Exception
    {
        this.saveTestUser();

        String json_userToCreate =
                "{\n  " +
                        "\"firstName\": \"Farbaks Driver\",\n  " +
                        "\"lastName\": \"Bakre\",\n  " +
                        "\"emailAddress\": \"faroukbakre@gmail.com\",\n  " +
                        "\"phoneNumber\": \"+447549404730\",\n  " +
                        "\"roleId\": 2,\n  " +
                        "\"password\": \"0000\",\n  " +
                        "\"address\": \"United Kingdom\"\n" +
                "}";

        mockMvc
                .perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_userToCreate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.message").value("Account with email already exists."));
    }

    @Test
    public void when_UserDoesNotExistDuringLogin_Expect_ErrorMessage() throws Exception {
        this.addressRepository.deleteAll();
        this.userRepository.deleteAll();

        String json_userToCreate = "{\n  \"emailAddress\": \"faroukbakre@gmail.com\",\n  \"password\": \"0000\"\n}";

        mockMvc
                .perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_userToCreate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.message").value("Account does not exist."));

    }

    @Test
    public void when_UserPasswordIncorrectDuringLogin_Expect_ErrorMessage() throws Exception {
        this.saveTestUser();

        String json_userToCreate =
                "{\n  " +
                        "\"emailAddress\": \"faroukbakre@gmail.com\",\n  " +
                        "\"password\": \"0001\"\n}";

        mockMvc
                .perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_userToCreate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("400"))
                .andExpect(jsonPath("$.message").value("Incorrect password."));
    }

    @Test
    public void when_UserEmailAndPasswordValid_Expect_Success() throws Exception {
        this.saveTestUser();

        String json_userToCreate =
                "{\n  " +
                        "\"emailAddress\": \"faroukbakre@gmail.com\",\n  " +
                        "\"password\": \"0000\"\n" +
                        "}";

        mockMvc
                .perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_userToCreate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("User login successful."));

    }

    @Test
    public void when_NoBearerTokenForProtectedEndpoints_Expect_ErrorMessage() throws Exception {
        mockMvc
                .perform(get("/users/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Token not provided"));
    }

    @Test
    public void when_BearerTokenInvalid_Expect_ErrorMessage() throws Exception {
        mockMvc
                .perform(get("/users/me")
                        .header("AUTHORIZATION", "Bearer qwerftgd")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid or expired token"));
    }

    @Test
    public void when_BearerTokenExists_Expect_Success() throws Exception {
        this.saveTestUser();

        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiYWRtaW4iXSwiaWF0IjoxNzA0NjIwNzY3LCJleHAiOjE3MDU0ODQ3Njd9.FibPwDo0ypeUHCU37Rempjb7FPWY9sMAy9P6Q6CMgxY";
        mockMvc
                .perform(get("/users/me")
                        .header("AUTHORIZATION", token)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());
    }

    public void saveTestUser() {
        this.addressRepository.deleteAll();
        this.userRepository.deleteAll();

        Role role = this.roleRepository.findById(1).orElse(null);

        User newUser = new User();
        newUser.setFirstName("Farouk");
        newUser.setLastName("Bakre");
        newUser.setEmailAddress("faroukbakre@gmail.com");
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
