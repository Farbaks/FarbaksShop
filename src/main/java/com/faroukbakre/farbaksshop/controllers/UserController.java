package com.faroukbakre.farbaksshop.controllers;

import com.faroukbakre.farbaksshop.dto.responses.DefaultResponseDTO;
import com.faroukbakre.farbaksshop.dto.requests.LoginUserRequestDTO;
import com.faroukbakre.farbaksshop.services.UserService;
import com.faroukbakre.farbaksshop.dto.requests.NewUserRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping( "/users")
@AllArgsConstructor
@Validated
public class UserController {

    private UserService userService;

    @GetMapping("hello")
    public String helloWorld() {
        return "Hello Worlds!!";
    }

    @PostMapping("")
    public DefaultResponseDTO createUser(@Valid @RequestBody NewUserRequestDTO data) {
        return this.userService.createUser(data);
    }

    @PostMapping("login")
    public DefaultResponseDTO loginUser(@Valid @RequestBody LoginUserRequestDTO data) {
        return this.userService.loginUser(data);
    }

    @GetMapping("me")
    public DefaultResponseDTO getDetails(HttpServletRequest request) {
        return this.userService.getUserDetails(request);
    }

    @GetMapping()
    public DefaultResponseDTO getAllUsers() {
        return this.userService.getAllUsers();
    }

    @DeleteMapping("{id}")
    public DefaultResponseDTO getDetails(@PathVariable(name = "id") int id) {
        return this.userService.deleteUser(id);
    }

}
