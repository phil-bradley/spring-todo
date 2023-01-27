/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.exception;

/**
 *
 * @author Philip.Bradley
 */
public class TodoNotFoundException extends TodoException {

    private final long id;

    public TodoNotFoundException(long id) {
        super("Could not find todo " + id);
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
