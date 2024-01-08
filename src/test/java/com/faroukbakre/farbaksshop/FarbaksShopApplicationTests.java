package com.faroukbakre.farbaksshop;

import com.faroukbakre.farbaksshop.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class FarbaksShopApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads()
    {
        assertNotNull(userController);
    }

}
