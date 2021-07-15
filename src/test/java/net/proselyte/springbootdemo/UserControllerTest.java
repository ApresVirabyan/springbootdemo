package net.proselyte.springbootdemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import net.proselyte.springbootdemo.controller.UserController;
import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void testAddUser(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = new User(1L,"vahag@mail.ru", "123456", new Date(2021, 07, 14), new Date(2021, 06,13));
        when(userService.saveUser(any(User.class))).thenReturn(user);

        ResponseEntity<User> responseEntity = userController.createUser(user);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");

    }

    @Test
    public void testFindAll(){
        User firstUser = new User(1L,"vahag@mail.ru", "123456", new Date(2021, 07, 14), new Date(2021, 06,13));
        User secondUser = new User(2L,"artur@mail.ru", "654321", new Date(2021, 07, 14), new Date(2021, 06,13));
        List<User> userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);

        ResponseEntity<List<User>> users = userController.findAll();

        when(userService.findAll()).thenReturn(userList);
        assertThat(users.getStatusCodeValue()).isEqualTo(201);
        assertThat(users.getHeaders().getLocation().getPath()).isEqualTo("/users");
    }
}
