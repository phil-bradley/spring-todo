/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.service;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */

public interface TodoService {

    Todo getTodoById(long id);

    Todo save(Todo todo);

    List<Todo> getTodosByOwner(User user);
    
    void delete(Todo todo);
}
