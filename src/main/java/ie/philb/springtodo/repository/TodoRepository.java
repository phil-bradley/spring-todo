/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.repository;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Philip.Bradley
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
 
    List<Todo> findByOwner(User owner);
}
