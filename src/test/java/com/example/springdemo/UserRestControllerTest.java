package com.example.springdemo;

import com.example.springdemo.controller.UserController;
import com.example.springdemo.core.user.User;
import com.example.springdemo.core.user.UserRequestParamsDTO;
import com.example.springdemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private final String baseUrl = "http://localhost:8080/users";

    @Test
    public void createUserValidTest() throws Exception {
        // arrange
        User user = new User("t@gmail.com", "N", "L", new Date(988874483000L), null, null);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.baseUrl + "/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        // act
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("User is created")));
    }

    @Test
    public void createUserInvalidAllTest() throws Exception {
        // arrange
        User user = new User(null, null, null, null, null, null);

        RequestBuilder request = MockMvcRequestBuilders
                .post(this.baseUrl + "/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        // act
        this.mvc.perform(request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("Validation error. Check 'errors' field for details.")));
    }

    @Test
    public void updateEmailTest() throws Exception {
        // arrange
        UserRequestParamsDTO requestParamsDTO = new UserRequestParamsDTO();
        requestParamsDTO.setEmail("den@gmail.com");

        RequestBuilder request = MockMvcRequestBuilders
                .patch(this.baseUrl + "/updateEmail/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(requestParamsDTO))
                .contentType(MediaType.APPLICATION_JSON);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("Email of user is updated")));
    }

    @Test
    public void updateTest() throws Exception {
        // arrange
        User userToUpdate = new User("t@gmail.com", "N", "L", new Date(988874483000L), null, null);

        RequestBuilder request = MockMvcRequestBuilders
                .put(this.baseUrl + "/update/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userToUpdate))
                .contentType(MediaType.APPLICATION_JSON);

        // act
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("User is updated")));
    }

    @Test
    public void deleteTest() throws Exception {
        // arrange
        RequestBuilder request = MockMvcRequestBuilders
                .delete(this.baseUrl + "/delete/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // act
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString("User is deleted")));
    }

    @Test
    public void getByBirthDateRangeTest() throws Exception {
        // arrange
        long start = 988874483000L;
        long end = 988874483000L;
        User user1 = new User("t@gmail.com", "N", "L", new Date(start), null, null);
        User user2 = new User("m@gmail.com", "M", "N", new Date(end), null, null);
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        Mockito.when(this.userService.findByBirthDateRange(new Date(start), new Date(end))).thenReturn(userList);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", String.valueOf(start));
        requestParams.add("to", String.valueOf(end));

        RequestBuilder request = MockMvcRequestBuilders
                .get(this.baseUrl + "/findByBirthDateRange")
                .params(requestParams)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // act
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("t@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("m@gmail.com"));
    }
}
