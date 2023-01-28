/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.service.impl;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.exception.TodoAccessException;
import ie.philb.springtodo.exception.TodoException;
import ie.philb.springtodo.exception.TodoNotFoundException;
import ie.philb.springtodo.exception.TodoStateException;
import ie.philb.springtodo.repository.TodoRepository;
import ie.philb.springtodo.service.TodoService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philip.Bradley
 */
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(@Autowired TodoRepository repository) {
        this.todoRepository = repository;
    }

    @Override
    public Todo getTodoById(long id, User user) throws TodoException {

        Todo todo = null;
        User owner = null;

        try {
            todo = todoRepository.findById(id).orElseThrow();
            owner = todo.getOwner();
        } catch (NoSuchElementException ex) {
            throw new TodoNotFoundException(id);
        }

        if (owner.getId() != user.getId()) {
            throw new TodoAccessException(id, user);
        }

        return todo;
    }

    @Override
    public Todo save(Todo todo, User user) throws TodoException {

        if (todo.getId() != 0) {
            // Validate original, if it's not found or doesn't belong to us
            // it will throw an exception
            Todo original = getTodoById(todo.getId(), user);
        }

        if (todo.getOwner() == null) {
            todo.setOwner(user);
        }

        if (todo.getOwner().getId() != user.getId()) {
            throw new TodoAccessException(0, user);
        }

        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getTodosByOwner(User owner) {
        return todoRepository.findByOwner(owner);
    }

    @Override
    public void delete(Todo todo, User user) throws TodoException {

        if (todo.getOwner().getId() != user.getId()) {
            throw new TodoAccessException(todo.getId(), user);
        }

        if (!todo.isComplete()) {
            throw new TodoStateException(todo);
        }

        todoRepository.delete(todo);
    }

}
