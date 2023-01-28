/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.unittests;

import ie.philb.springtodo.domain.TodoStatus;
import ie.philb.springtodo.repository.TodoStatusConverter;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Philip.Bradley
 */
public class TodoStatusConversionTests {

    @Test
    public void todoStatusRetrievedFromCode() {
        TodoStatus roundTripStatus = TodoStatus.fromCode(TodoStatus.Pending.code());
        assertEquals("Round trip status matches", TodoStatus.Pending, roundTripStatus);
    }

    @Test
    public void nullStatusRetrievedFromInvalidCode() {
        TodoStatus retrieved = TodoStatus.fromCode("ABCDEFG");
        assertNull("Non existing status returns null", retrieved);
    }

    @Test
    public void converterUsesStatusCode() {
        TodoStatusConverter converter = new TodoStatusConverter();
        TodoStatus status = TodoStatus.Complete;

        String convertedCode = converter.convertToDatabaseColumn(status);
        assertEquals("Convert status to code for DB", convertedCode, status.code());
    }

    @Test
    public void converterMapsStatusCodeToStatus() {
        TodoStatusConverter converter = new TodoStatusConverter();
        TodoStatus status = TodoStatus.Complete;

        TodoStatus mappedStatus = converter.convertToEntityAttribute(status.code());
        assertEquals("Convert code in DB to Status enum", status, mappedStatus);
    }
}
