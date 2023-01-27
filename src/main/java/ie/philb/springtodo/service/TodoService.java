/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.service;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.exception.TodoException;
import java.util.List;

/**
 *
 * @author Philip.Bradley
 */

public interface TodoService {

    Todo getTodoById(long id, User user) throws TodoException;

    Todo save(Todo todo, User user) throws TodoException;

    List<Todo> getTodosByOwner(User user);
    
    void delete(Todo todo, User user) throws TodoException;
}
