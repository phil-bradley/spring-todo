/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ie.philb.springtodo.common;

import ie.philb.springtodo.domain.User;

/**
 *
 * @author Philip.Bradley
 */
public class UserBuilder {

    private long userId;
    private String login;

    public UserBuilder withId(long id) {
        this.userId = id;
        return this;
    }

    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(userId);
        user.setLogin(login);
        return user;
    }
}
