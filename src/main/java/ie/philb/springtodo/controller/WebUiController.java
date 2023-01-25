/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Philip.Bradley
 */
@Controller
public class WebUiController {

    // Default page will be the "home" page based on home.html defined in templates
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
}
