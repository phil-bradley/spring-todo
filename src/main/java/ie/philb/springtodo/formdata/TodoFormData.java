/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.formdata;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Philip.Bradley
 */
@Data
public class TodoFormData {

    @NotEmpty(message = "Title field can't be empty")
    @Size(min = 1, max = 32)
    private String title;

    @NotEmpty(message = "Description field can't be empty")
    @Size(min = 1, max = 2048)
    private String description;
}
