/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.integrationtests;

import ie.philb.springtodo.controller.TodoController;
import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.service.TodoService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author Philip.Bradley
 */
@WebMvcTest(TodoController.class)
public class TodoRestServiceTests {

    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unauthenticatedRequestIsDenied() throws Exception {

        mockMvc.perform(get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }

    @Test
    @Disabled
    public void listTodosReturnsArrayOfTodo() throws Exception {
        when(todoService.getTodosByOwner(any())).thenReturn(createTodoList());

        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$.todos[0].title").isNotEmpty());
    }

    @Test
    @Disabled
    public void getTodoReturnsSingleTodo() throws Exception {
        when(todoService.getTodoById(1234)).thenReturn(buildTodo());

        mockMvc.perform(get("/todo/1234"))
                .andExpect(jsonPath("$.title").isNotEmpty());
    }

    private List<Todo> createTodoList() {
        Todo todo = buildTodo();
        List<Todo> list = new ArrayList<>();
        list.add(todo);

        return list;
    }

    private Todo buildTodo() {
        Todo todo = new Todo();
        todo.setId(1234);
        todo.setCreated(LocalDateTime.now());
        todo.setUpdated(todo.getCreated());
        todo.setDescription("The description");
        todo.setTitle("The Title");
        todo.setStatus(TodoStatus.Pending);

        User owner = new User();
        owner.setId(222);
        owner.setLogin("philb");

        todo.setOwner(owner);
        return todo;
    }

}
