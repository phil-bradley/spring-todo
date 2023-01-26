/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.repository;

import ie.philb.springtodo.domain.TodoStatus;
import javax.persistence.AttributeConverter;

/**
 *
 * @author Philip.Bradley
 */
public class TodoStatusConverter implements AttributeConverter<TodoStatus, String> {

    @Override
    public String convertToDatabaseColumn(TodoStatus todoStatus) {

        if (todoStatus == null) {
            return null;
        }

        return todoStatus.code();
    }

    @Override
    public TodoStatus convertToEntityAttribute(String code) {
        return TodoStatus.fromCode(code);
    }

}
