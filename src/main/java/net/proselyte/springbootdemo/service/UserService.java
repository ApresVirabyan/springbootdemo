package net.proselyte.springbootdemo.service;

import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User service.
 */
@Service
public class UserService {

    /** User repository. */
    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository repository.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Find user by id.
     *
     * @param id user id.
     * @return user.
     */
    public User findById(Long id){
        return userRepository.getOne(id);
    }

    /**
     * Find all users.
     *
     * @return list of all users.
     */
    public List<User> findAll(){
        return userRepository.findAll();
    }

    /**
     * Save user.
     *
     * @param user user.
     * @return saved user.
     */
    public User saveUser(User user){
        return userRepository.save(user);
    }

    /**
     *
     * @param id user id.
     * @return true if user is deleted otherwise false.
     */
    public boolean deleteById(Long id){
        if(id != null){
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
