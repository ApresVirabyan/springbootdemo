package net.proselyte.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Object for transfer note`s data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {

    /** Note id.*/
    private Long id;

    /** Note title.*/
    private String title;

    /** Note description. */
    private String note;

    /** Note creation date. */
    private Date createTime;

    /** Time of last update note. */
    private Date lastUpdateTime;
}
