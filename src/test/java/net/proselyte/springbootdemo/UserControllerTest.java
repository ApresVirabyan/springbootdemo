package net.proselyte.springbootdemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.proselyte.springbootdemo.controller.UserController;
import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getAllUsersTest() throws Exception {
        User firstUser = new User(1L, "apres1987@mail.ru", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14));
        User secondUser = new User(1L, "apres1987@mail.ru", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14));
        List<User> users = new ArrayList<>();
        users.add(firstUser);
        users.add(secondUser);
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/user/users")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*]").exists());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        User user = new User(1L, "apres1987@mail.ru", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(userService.findById(1L)).thenReturn(user);
        mockMvc.perform(get("/user/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void createUser() throws Exception {
        User user = new User(3L, "email4@mail.com", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(userService.saveUser(user)).thenReturn(user);
        mockMvc.perform(post("/user/createUser")
                .content(asJsonString(new User(3L, "email4@mail.com", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateUser() throws Exception {
        User user = new User(3L, "email4@mail.com", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(userService.findById(3L)).thenReturn(user);
        when(userService.saveUser(user)).thenReturn(user);
        mockMvc.perform(put("/user/update/{id}", 3)
                .content(asJsonString(new User(3L, "email4@mail.com", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("123456"))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.email").value("email4@mail.com"));
    }

    @Test
    public void deleteUser() throws Exception {
        User user = new User(3L, "email4@mail.com", "123456", new Date(2020, 11, 13), new Date(2019, 06, 14));
        when(userService.deleteById(user.getId())).thenReturn(true);
        mockMvc.perform(delete("/user/delete/{id}", 3L)).andExpect(status().isOk());
    }
}
