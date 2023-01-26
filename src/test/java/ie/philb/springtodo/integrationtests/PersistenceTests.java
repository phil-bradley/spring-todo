/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.integrationtests;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.repository.TodoRepository;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Philip.Bradley
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PersistenceTests {

    private User owner;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepository;

    @Before
    public void createOwnerAccount() {

        if (this.owner == null) {
            this.owner = new User();
            this.owner.setLogin("philb");
            this.owner = entityManager.persist(owner);
        }
    }

    @Test
    public void fetchByIdFieldsMatchSource() {

        Todo todo = new Todo();
        todo.setTitle("The Title");
        todo.setDescription("The Description");
        todo.setOwner(owner);

        todo = todoRepository.save(todo);

        Todo found = todoRepository.getById(todo.getId());
        assertNotNull("Found TODO by Id", found);

        assertEquals("Title matches", todo.getTitle(), found.getTitle());
        assertEquals("Description matches", todo.getDescription(), found.getDescription());
        assertEquals("", TodoStatus.Pending, found.getStatus());
    }

    @Test
    public void fetchByIdOwnerMatchesSource() {

        Todo todo = new Todo();
        todo.setTitle("The Title");
        todo.setDescription("The Description");
        todo.setOwner(owner);

        todo = todoRepository.save(todo);

        Todo found = todoRepository.getById(todo.getId());
        assertNotNull("Found TODO by Id", found);

        User foundOwner = found.getOwner();
        assertNotNull("Todo owner retrieved", owner);
        assertEquals(owner.getLogin(), foundOwner.getLogin());
    }

    @Test
    @Ignore
    public void fetchByIdPopulatesTimestamps() {

        // TODO Timestamps not retrieved by getById but are in findByOwner
        Todo todo = new Todo();
        todo.setTitle("The Title");
        todo.setDescription("The Description");
        todo.setOwner(owner);

        todo = todoRepository.save(todo);

        Todo found = todoRepository.getById(todo.getId());
        assertNotNull("Found TODO by Id", found);

        assertNotNull("Created timestamp populated", found.getCreated());
        assertNotNull("Update timestamp populated", found.getUpdated());
    }

    @Test
    public void retrievalByOwnerReturnsEntries() {

        Todo todo_1 = new Todo();
        todo_1.setTitle("The Title #1");
        todo_1.setDescription("The Description #1");
        todo_1.setOwner(owner);

        todo_1 = entityManager.persist(todo_1);

        Todo todo_2 = new Todo();
        todo_2.setTitle("The Title #2");
        todo_2.setDescription("The Description #2");
        todo_2.setOwner(owner);

        todo_1 = todoRepository.save(todo_1);
        todo_2 = todoRepository.save(todo_2);

        List<Todo> found = todoRepository.findByOwner(owner);
        assertNotNull("Found list of TODO by owner", found);
        assertEquals("", 2, found.size());

    }
}
