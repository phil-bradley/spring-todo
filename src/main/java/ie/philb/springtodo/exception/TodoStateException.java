/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.exception;

import ie.philb.springtodo.domain.Todo;

/**
 *
 * @author Philip.Bradley
 */
public class TodoStateException extends TodoException {

    private final Todo todo;

    public TodoStateException(Todo todo) {
        super("Todo state exception " + todo);
        this.todo = todo;
    }

    public Todo getTodo() {
        return todo;
    }

}
