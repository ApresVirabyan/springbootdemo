package net.proselyte.springbootdemo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @NotBlank
    private String password;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private HashSet<Note> notes = new HashSet();

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
