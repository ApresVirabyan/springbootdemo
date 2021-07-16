package net.proselyte.springbootdemo;

import net.proselyte.springbootdemo.model.Note;
import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.repository.UserRepository;
import net.proselyte.springbootdemo.service.UserService;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Spy
    private UserRepository userRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllUsersTest(){
        List<User> users = new ArrayList<>();
        User firstUser = new User(1L,"vahag@mail.ru", "123456", new Date(2021, 07, 14), new Date(2021, 06,13));
        User secondUser = new User(2L,"artur@mail.ru", "654321", new Date(2021, 07, 14), new Date(2021, 06,13));

        users.add(firstUser);
        users.add(secondUser);

        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.findAll();
        assertEquals(2,userList.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUserByIdTest(){
        when(userRepository.getOne(1L)).thenReturn(new User(1L,"vahag@mail.ru", "123456", new Date(2021, 07, 14), new Date(2021, 06,13)));
        User user = userService.findById(1L);
        assertEquals("vahag@mail.ru", user.getEmail());
        assertEquals(new Date(2021, 07, 14),user.getCreateTime());
        assertEquals(new Date(2021, 06,13), user.getLastUpdateTime());
    }

    @Test
    public void createUserTest(){
        User user = new User(1L,"vahag@mail.ru", "123456", new Date(2021, 07, 14), new Date(2021, 06,13));
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void deleteNote(){
        User user = new User(1L,"vahag@mail.ru", "123456", new Date(2021, 07, 14), new Date(2021, 06,13));
        userService.deleteById(1L);
        verify(userRepository).deleteById(user.getId());
    }
}
