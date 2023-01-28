/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.common;

import ie.philb.springtodo.domain.Todo;
import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.domain.User;

/**
 *
 * @author Philip.Bradley
 */
public class TodoBuilder {

    private long id;
    private User owner;
    private TodoStatus todoStatus = TodoStatus.Pending;

    public TodoBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public TodoBuilder withOwner(User user) {
        this.owner = user;
        return this;
    }

    public TodoBuilder withStatus(TodoStatus status) {
        this.todoStatus = status;
        return this;
    }

    public Todo build() {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setOwner(owner);
        todo.setStatus(todoStatus);
        return todo;
    }
}
