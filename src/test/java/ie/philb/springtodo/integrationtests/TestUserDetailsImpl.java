/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.integrationtests;

import ie.philb.springtodo.auth.TodoAppUserPrincipal;
import ie.philb.springtodo.common.UserBuilder;
import ie.philb.springtodo.domain.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Philip.Bradley
 */
@Service
@Primary
@Profile("test")
public class TestUserDetailsImpl implements UserDetailsService {

    private final Map<String, TodoAppUserPrincipal> userMap = new HashMap<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        initData();

        if (userMap.containsKey(username)) {
            return userMap.get(username);
        }

        throw new UsernameNotFoundException(username);
    }

    private void initData() {
        addUser(new UserBuilder().withId(1).withLogin("fred").build());
        addUser(new UserBuilder().withId(2).withLogin("bob").build());
    }

    private void addUser(User user) {
        userMap.put(user.getLogin(), new TodoAppUserPrincipal(user));
    }
}
