package com.example.springdemo;

import com.example.springdemo.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class SpringDemoApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        assertThat(this.userController).isNotNull();
    }

}
