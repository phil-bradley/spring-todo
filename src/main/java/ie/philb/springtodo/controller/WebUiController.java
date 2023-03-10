/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.controller;

import ie.philb.springtodo.auth.TodoAppUserPrincipal;
import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.domain.dto.TodoDto;
import ie.philb.springtodo.exception.TodoException;
import ie.philb.springtodo.exception.TodoStateException;
import ie.philb.springtodo.service.TodoService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(WebUiController.class);

    private final TodoService todoService;

    @Autowired
    public WebUiController(TodoService todoService) {
        this.todoService = todoService;
    }

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
    public String addTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @Valid TodoDto todoDto, BindingResult result, Model model) throws TodoException {

        if (result.hasErrors()) {
            return "todoform";
        }

        Todo todo = todoDto.getTodo();
        todo.setOwner(user.getUser());

        Todo saved = todoService.save(todo, user.getUser());
        logger.info("Saved todo {}", saved);
        return "redirect:/todos";
    }

    @GetMapping("/update/{id}")
    public String showUpdateTodoForm(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id, Model model) throws TodoException {

        Todo todo = null;
        try {
            todo = todoService.getTodoById(id, user.getUser());
            model.addAttribute("todo", todo);
        } catch (TodoException tdx) {
            model.addAttribute("exception", tdx);
            return "error";
        }

        if (todo.isComplete()) {
            model.addAttribute("exception", new TodoStateException(todo));
            return "error";
        }

        return "update-todo";
    }

    @PutMapping("/update/{id}")
    public String updateTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id, @Valid TodoDto todoDto, BindingResult result, Model model) throws TodoException {

        if (result.hasErrors()) {
            return "update-todo";
        }

        Todo original = null;
        try {
            original = todoService.getTodoById(id, user.getUser());
            original.setDescription(todoDto.getDescription());
            original.setTitle(todoDto.getTitle());

            todoService.save(original, user.getUser());

        } catch (TodoException tdx) {
            model.addAttribute("exception", tdx);
            return "error";
        }

        if (original.isComplete()) {
            model.addAttribute("exception", new TodoStateException(original));
            return "error";
        }

        return "redirect:/todos";
    }

    @PostMapping("/complete/{id}")
    public String completeTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id, Model model) throws TodoException {

        try {
            Todo todo = todoService.getTodoById(id, user.getUser());

            if (todo.isComplete()) {
                logger.error("Could not complete already completed todo {}, user {}", id, user);
                throw new TodoStateException(todo);
            }

            todo.setStatus(TodoStatus.Complete);
            todoService.save(todo, user.getUser());

        } catch (TodoException tdx) {
            model.addAttribute("exception", tdx);
            return "error";
        }

        return "redirect:/todos";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTodo(@AuthenticationPrincipal TodoAppUserPrincipal user, @PathVariable("id") long id, Model model) throws TodoException {

        try {
            Todo todo = todoService.getTodoById(id, user.getUser());
            todoService.delete(todo, user.getUser());
        } catch (TodoException tdx) {
            model.addAttribute("exception", tdx);
            return "error";
        }

        return "redirect:/todos";
    }
}
