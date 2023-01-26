/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.controller;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.formdata.TodoFormData;
import ie.philb.springtodo.service.TodoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/todos")
    public String todos(Model model) {
        User user = new User();
        user.setId(1);
        List<Todo> todos = todoService.getTodosByOwner(user);

        model.addAttribute("todos", todos);
        return "todos";
    }

    @GetMapping("/newtodo")
    public String showTodoForm(TodoFormData todoFormData) {
        return "todoform";
    }

    @PostMapping("/newtodo")
    public String addTodo(@Valid TodoFormData todoFormData, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "todoform";
        }
        
        Todo todo = new Todo();
        todo.setTitle(todoFormData.getTitle());
        todo.setDescription(todoFormData.getDescription());
        
        todoService.save(todo);
        return "redirect:/todos";
    }
}
