package net.proselyte.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Object for transfer user`s data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /** User id. */
    private Long id;

    /** User email. */
    private String email;

    /** User password. */
    private String password;

    /** User create time. */
    private Date createTime;

    /** Time of last update user. */
    private Date lastUpdateTime;
}
