/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.controller;

import ie.philb.springtodo.exception.TodoException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Philip.Bradley
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(TodoException.class)
    public String todoException(Exception ex, Model model) {
        model.addAttribute("exception", ex);
        return "error";
    }

}
