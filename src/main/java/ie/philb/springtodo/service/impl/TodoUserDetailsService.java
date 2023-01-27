/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.service.impl;

import ie.philb.springtodo.auth.TodoAppUserPrincipal;
import ie.philb.springtodo.domain.User;
import ie.philb.springtodo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philip.Bradley
 */
@Service
public class TodoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public TodoUserDetailsService(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByLogin(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        
        return new TodoAppUserPrincipal(user);
    }
}