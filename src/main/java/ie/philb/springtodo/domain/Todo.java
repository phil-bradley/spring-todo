/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import lombok.Data;

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

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @ManyToOne
    private User owner;

    private String title;

    private String description;

    private TodoStatus status;

}
