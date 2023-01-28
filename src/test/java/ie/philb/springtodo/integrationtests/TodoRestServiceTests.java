/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.integrationtests;

import ie.philb.springtodo.common.WithCustomUser;
import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.service.TodoService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author Philip.Bradley
 */
//@WebMvcTest(TodoController.class, )
@SpringBootTest
@AutoConfigureMockMvc
public class TodoRestServiceTests {

    @MockBean
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void unauthorisedRequestToLoginPageOk() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk());
    }

    @Test
    public void unauthorisedRequestToEndpointDenied() throws Exception {
        when(todoService.getTodosByOwner(any())).thenReturn(createTodoList());
        mockMvc.perform(get("/todos")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithCustomUser(username = "bobs")
    @Disabled
    public void authorisedRequestReturnsOk() throws Exception {
        mockMvc.perform(get("/todos")).andExpect(status().isOk());
    }

    @Test
    @WithCustomUser(username = "bobs")
    @Disabled
    public void listTodosReturnsArrayOfTodo() throws Exception {
        when(todoService.getTodosByOwner(any())).thenReturn(createTodoList());
        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$.todos[0].title").isNotEmpty());
    }

    @Test
    @Disabled
    @WithCustomUser(username = "bobs")
    public void getTodoReturnsSingleTodo() throws Exception {
        when(todoService.getTodoById(1, any())).thenReturn(buildTodo());

        mockMvc.perform(get("/todo/1"))
                .andExpect(jsonPath("$.title").isNotEmpty());
    }

    @Test
    @Disabled
    @WithCustomUser(username = "bobs")
    public void getNonExistingTodoReturnsNotFound() throws Exception {
        mockMvc.perform(get("/todo/1234")).andExpect(status().isNotFound());

    }

    @Test
    @Disabled
    @WithCustomUser(username = "bobs")
    public void getTodoNotOwnedReturnsUnuthorised() throws Exception {
        when(todoService.getTodoById(1, any())).thenReturn(buildTodo());
        mockMvc.perform(get("/todo/1234")).andExpect(status().isUnauthorized());

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

    private User buildUser() {
        User user = new User();
        user.setId(1);
        user.setLogin("fred");
        user.setFirstName("Fred");
        user.setSurName("Bloggs");

        return user;
    }

}
