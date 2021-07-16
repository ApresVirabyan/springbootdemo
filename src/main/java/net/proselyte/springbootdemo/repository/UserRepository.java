package net.proselyte.springbootdemo.repository;

import net.proselyte.springbootdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for crud operations with user.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
