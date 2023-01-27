/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.controller;

import ie.philb.springtodo.auth.TodoAppUserPrincipal;
import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.domain.dto.TodoDto;
import ie.philb.springtodo.service.TodoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 *
 * @author Philip.Bradley
 */
@Controller
public class WebUiController {

    private final TodoService todoService;

    @Autowired
    public WebUiController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Default page will be the "home" page based on home.html defined in templates
    // Contains meta data about the project
    @GetMapping("/")
    public String home(@AuthenticationPrincipal TodoAppUserPrincipal user, Model model) {
        model.addAttribute("currentUser", user);
        return "home";
    }

    @GetMapping("/todos")
    public String todos(@AuthenticationPrincipal TodoAppUserPrincipal user, Model model) {
        List<Todo> todos = todoService.getTodosByOwner(user.getUser());
        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/newtodo")
    public String showTodoForm(TodoDto todoDto) {
        return "todoform";
    }

    @PostMapping("/newtodo")
    public String addTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @Valid TodoDto todoDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "todoform";
        }

        Todo todo = todoDto.getTodo();
        todo.setOwner(user.getUser());

        todoService.save(todo);
        return "redirect:/todos";
    }

    @GetMapping("/update/{id}")
    public String showUpdateTodoForm(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id, Model model) {

        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            throw new IllegalArgumentException("Cannot find entry " + id);
        }

        if (todo.getOwner().getId() != user.getUser().getId()) {
            throw new IllegalArgumentException("Cannot modify entry " + id);
        }

        model.addAttribute("todo", todo);
        return "update-todo";
    }

    @PutMapping("/update/{id}")
    public String updateTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id, @Valid TodoDto todoDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "update-todo";
        }

        Todo original = todoService.getTodoById(id);

        if (original == null) {
            throw new IllegalArgumentException("Cannot find entry " + id);
        }

        if (original.getOwner().getId() != user.getUser().getId()) {
            throw new IllegalArgumentException("Cannot modify entry " + id);
        }

        original.setDescription(todoDto.getDescription());
        original.setTitle(todoDto.getTitle());

        todoService.save(original);

        return "redirect:/todos";
    }

    @PostMapping("/complete/{id}")
    public String completeTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id) {

        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            throw new IllegalArgumentException("Cannot find entry " + id);
        }

        if (todo.getOwner().getId() != user.getUser().getId()) {
            throw new IllegalArgumentException("Cannot modify entry " + id);
        }

        if (todo.isComplete()) {
            throw new IllegalArgumentException("Cannot complete entry " + id);
        }

        todo.setStatus(TodoStatus.Complete);
        todoService.save(todo);

        return "redirect:/todos";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id) {

        Todo todo = todoService.getTodoById(id);

        if (todo == null) {
            throw new IllegalArgumentException("Cannot find entry " + id);
        }

        if (todo.getOwner().getId() != user.getUser().getId()) {
            throw new IllegalArgumentException("Cannot modify entry " + id);
        }

        if (!todo.isComplete()) {
            throw new IllegalArgumentException("Cannot delete entry " + id + " with status " + todo.getStatus());
        }

        todoService.delete(todo);

        return "redirect:/todos";
    }
}
