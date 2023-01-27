/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.exception;

/**
 *
 * @author Philip.Bradley
 */
public class TodoException extends Exception {

    public TodoException(String msg) {
        super(msg);
    }

    public TodoException(String msg, Throwable t) {
        super(msg, t);
    }
}
