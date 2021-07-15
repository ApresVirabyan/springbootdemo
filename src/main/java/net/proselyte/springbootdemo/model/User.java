package net.proselyte.springbootdemo.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    public User() {
    }

    public User(Long id, String email, String password, Date createTime, Date lastUpdateTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
    }
}
