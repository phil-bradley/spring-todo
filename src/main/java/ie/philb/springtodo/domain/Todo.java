/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author Philip.Bradley
 */
@Data
@Entity
public class Todo implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_id_seq")
    private int id;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime updated;

    @ManyToOne
    private User owner;

    @NotEmpty(message = "Title field can't be empty")
    private String title;

    @NotEmpty(message = "Description field can't be empty")
    private String description;

    private TodoStatus status = TodoStatus.Pending;

}
