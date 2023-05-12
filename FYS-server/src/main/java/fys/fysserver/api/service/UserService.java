package fys.fysserver.api.service;

import fys.fysmodel.User;
import fys.fyspersistence.users.UsersDbRepository;
import fys.fyspersistence.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UsersRepository usersRepository;

    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserService() {

    }

    public User login(String username, String password) {
        System.out.println("login: " + username + " " + password);

        return usersRepository.findByUsernameAndPassword(username, password);
    }

    public User register(String username, String password, String firstName, String lastName) {
        System.out.println("register: " + username + " " + password + " " + firstName + " " + lastName);

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);

        usersRepository.add(user);
        user = usersRepository.findByUsernameAndPassword(username, password);

        return user;
    }
}
