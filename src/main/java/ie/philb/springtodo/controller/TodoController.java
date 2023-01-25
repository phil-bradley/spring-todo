/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.controller;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.service.TodoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final TodoService todoService;

    public TodoController(@Autowired TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public List<Todo> getTodos() {
        User user = new User();
        return todoService.getTodosByOwner(user);
    }
}
