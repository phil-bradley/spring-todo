/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.unittests;

import ie.philb.springtodo.common.TodoBuilder;
import ie.philb.springtodo.common.UserBuilder;
import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.exception.TodoAccessException;
import ie.philb.springtodo.exception.TodoException;
import ie.philb.springtodo.exception.TodoNotFoundException;
import ie.philb.springtodo.exception.TodoStateException;
import ie.philb.springtodo.repository.TodoRepository;
import ie.philb.springtodo.service.TodoService;
import ie.philb.springtodo.service.impl.TodoServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Philip.Bradley
 */
public class TodoServiceTests {

    private TodoRepository todoRepository;

    private final Map<Long, User> userMap = new HashMap<>();
    private final Map<Long, Todo> todoMap = new HashMap<>();

    @Test
    public void testTodoRetrievedById() throws Exception {

        initData();
        initMocks();

        TodoService todoService = new TodoServiceImpl(todoRepository);
        Todo found = todoService.getTodoById(1, userMap.get(1L));

        assertNotNull("Todo retrived", found);
    }

    @Test(expected = TodoNotFoundException.class)
    public void testTodoNotFoundThrows_TodoNotFoundException() throws TodoException {

        initData();
        initMocks();

        TodoService todoService = new TodoServiceImpl(todoRepository);
        todoService.getTodoById(17, userMap.get(1L));
    }

    @Test(expected = TodoAccessException.class)
    public void testWrongOwnerThrows_TodoAccessException() throws TodoException {

        initData();
        initMocks();

        User user = userMap.get(2L);

        TodoService todoService = new TodoServiceImpl(todoRepository);
        todoService.getTodoById(1, user);
    }

    @Test
    public void testDeleteTodo() throws TodoException {

        initData();
        initMocks();

        User user = userMap.get(1L);
        Todo todo = new TodoBuilder().withId(10).withOwner(user).withStatus(TodoStatus.Complete).build();

        TodoService todoService = new TodoServiceImpl(todoRepository);
        todoService.delete(todo, user);
    }

    @Test(expected = TodoStateException.class)
    public void testDeleteWrongStatusThrows_TodoStateException() throws TodoException {

        initData();
        initMocks();

        User user = userMap.get(1L);
        Todo todo = new TodoBuilder().withId(10).withOwner(user).withStatus(TodoStatus.Pending).build();

        TodoService todoService = new TodoServiceImpl(todoRepository);
        todoService.delete(todo, user);
    }

    @Test(expected = TodoAccessException.class)
    public void testDeleteWrongOwnerThrows_TodoAccessException() throws TodoException {

        initData();
        initMocks();

        User user1 = userMap.get(1L);
        User user2 = userMap.get(2L);
        Todo todo = new TodoBuilder().withId(10).withOwner(user2).withStatus(TodoStatus.Complete).build();

        TodoService todoService = new TodoServiceImpl(todoRepository);
        todoService.delete(todo, user1);
    }

    @Test(expected = TodoAccessException.class)
    public void testSaveWithWrongOwnerThrows_TodoAccessException() throws TodoException {

        initData();
        initMocks();

        User user1 = userMap.get(1L);
        User user2 = userMap.get(2L);
        Todo todo = new TodoBuilder().withId(0).withOwner(user2).withStatus(TodoStatus.Complete).build();

        TodoService todoService = new TodoServiceImpl(todoRepository);
        todoService.save(todo, user1);
    }

    @Test(expected = TodoNotFoundException.class)
    public void testSaveNonExistingTodoThrows_TodoAccessException() throws TodoException {

        initData();
        initMocks();

        User user1 = userMap.get(1L);
        User user2 = userMap.get(2L);
        Todo todo = new TodoBuilder().withId(42).withOwner(user2).withStatus(TodoStatus.Complete).build();

        TodoService todoService = new TodoServiceImpl(todoRepository);
        todoService.save(todo, user1);
    }

    private List<Todo> getTodosByOwner(User owner) {
        return todoMap.values().stream().filter(todo -> todo.getOwner().getId() == owner.getId()).collect(Collectors.toList());
    }

    private void initMocks() {
        when(todoRepository.findByOwner(userMap.get(1L))).thenReturn(getTodosByOwner(userMap.get(1L)));
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todoMap.get(1L)));
    }

    private void initData() {

        this.todoRepository = mock(TodoRepository.class);

        User user1 = new UserBuilder().withId(1).withLogin("fred").build();
        userMap.put(user1.getId(), user1);

        User user2 = new UserBuilder().withId(2).withLogin("bob").build();
        userMap.put(user2.getId(), user2);

        Todo todo1 = new TodoBuilder().withId(1).withOwner(user1).build();
        todoMap.put(todo1.getId(), todo1);

        Todo todo2 = new TodoBuilder().withId(2).withOwner(user1).build();
        todoMap.put(todo2.getId(), todo2);

        Todo todo3 = new TodoBuilder().withId(3).withOwner(user2).build();
        todoMap.put(todo3.getId(), todo3);
    }
}
