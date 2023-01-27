/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.exception;

import ie.philb.springtodo.domain.User;

/**
 *
 * @author Philip.Bradley
 */
public class TodoAccessException extends TodoException {

    private final long todoId;
    private final User user;

    public TodoAccessException(long id, User user) {
        super("User " + user.getLogin() + " cannot access entry " + id);
        this.todoId = id;
        this.user = user;
    }

    public long getTodoId() {
        return todoId;
    }

    public User getUser() {
        return user;
    }
}