package net.proselyte.springbootdemo.controller;

import net.proselyte.springbootdemo.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for work with user.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /** User service. */
    private final UserService userService;

    /**
     * Constructor.
     *
     * @param userService service.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method for show all users.
     *
     * @return response with all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll().stream().map(this::convertToUserDto).collect(Collectors.toList());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Method for show one user.
     *
     * @param id user id.
     * @return response which contains user with passed id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long id) {
        User user = userService.findById(id);
        if (user != null) {
            UserDto userDto = convertToUserDto(user);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method for creating user.
     *
     * @param userDto user DTO.
     * @return response which contains created user.
     */
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = convertToUser(userDto);
        User createdUser = userService.saveUser(user);
        if (createdUser != null) {
            return new ResponseEntity<>(convertToUserDto(createdUser), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Delete user.
     *
     * @param id deleted user id.
     * @return Http status.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        if (userService.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Update user.
     *
     * @param id user id.
     * @param userForUpdate user with updated data.
     * @return response with updated user.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userForUpdate) {
        User user = userService.findById(id);
        if (user != null) {
            user.setEmail(userForUpdate.getEmail());
            user.setPassword(userForUpdate.getPassword());
            user.setCreateTime(userForUpdate.getCreateTime());
            user.setLastUpdateTime(userForUpdate.getLastUpdateTime());
            return new ResponseEntity<>(convertToUserDto(userService.saveUser(user)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Convert user to user DTO.
     *
     * @param user user.
     * @return user DTO.
     */
    private UserDto convertToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setCreateTime(user.getCreateTime());
        userDto.setLastUpdateTime(user.getLastUpdateTime());
        return userDto;
    }

    /**
     * Convert userDTO to user.
     *
     * @param userDto user DTO.
     * @return user.
     */
    private User convertToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCreateTime(userDto.getCreateTime());
        user.setLastUpdateTime(userDto.getLastUpdateTime());
        return user;
    }
}
