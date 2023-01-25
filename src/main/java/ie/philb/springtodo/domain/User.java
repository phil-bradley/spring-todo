/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author Philip.Bradley
 */
@Data
@Entity
public class User implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private int id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastUpdated;

    private String login;
    
    private String firstName;
    
    private String surName;

    private String passwordHash;

    private String passwordSalt;
}
