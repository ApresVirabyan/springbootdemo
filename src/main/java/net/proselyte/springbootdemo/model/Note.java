package net.proselyte.springbootdemo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "note")
    private String note;
    @Column(name="create_time")
    private Date createTime;
    @Column(name = "last_update_time")
    private Date lastUpdateTime;
}
