/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.controller;

import ie.philb.springtodo.auth.TodoAppUserPrincipal;
import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.service.TodoService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Philip.Bradley
 */
@RestController
@RequestMapping(path = "/todos")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    private final TodoService todoService;

    public TodoController(@Autowired TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<Todo> getTodos() {
        return todoService.getTodosByOwner(user());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Todo> getTodo(@PathVariable("id") long id) {

        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            logger.error("Could not find todo {}, user {}", id, user());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (todo.getOwner().getId() != user().getId()) {
            logger.error("Could not modify todo {} from user {}", id, user());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    private User user() {
        // TODO: There should be a way to inject this
        TodoAppUserPrincipal user = (TodoAppUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUser();
    }
}
