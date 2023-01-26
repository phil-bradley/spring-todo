/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.domain;

import ie.philb.springtodo.repository.TodoStatusConverter;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author Philip.Bradley
 */
@Data
@Entity
@Table(indexes = @Index(name = "todo_user_idx", columnList = "OWNER_ID"))
public class Todo implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_id_seq")
    private long id;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @ManyToOne
    private User owner;

    @NotEmpty
    @Size(min = 1, max = 32)
    private String title;

    @NotEmpty
    @Size(min = 1, max = 2048)
    private String description;

    @Convert(converter = TodoStatusConverter.class)
    private TodoStatus status = TodoStatus.Pending;

}
