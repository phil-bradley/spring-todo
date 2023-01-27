/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.service.impl;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.repository.TodoRepository;
import ie.philb.springtodo.service.TodoService;
import java.util.List;
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
    public Todo getTodoById(long id) {
        return todoRepository.getById(id);
    }

    @Override
    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getTodosByOwner(User owner) {
        return todoRepository.findByOwner(owner);
    }

    @Override
    public void delete(Todo todo) {

        if (!todo.isComplete()) {
            throw new IllegalStateException("Cannot delete todo with status " + todo.getStatus());
        }

        todoRepository.delete(todo);
    }

}
