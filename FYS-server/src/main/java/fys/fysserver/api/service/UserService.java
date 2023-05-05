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

    private UsersDbRepository usersRepository;

    private List<User> userList;

    //@Autowired
    // TODO: ask about this
    public void setUsersRepository(UsersDbRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserService() {
        userList = new ArrayList<>();
    }

    public User login(String username, String password) {
        System.out.println("login: " + username + " " + password);

        for(User user:userList)
            if(user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;

        throw new RuntimeException("User is not registered");

        //TODO: uncomment this
        //return usersRepository.findByUsernameAndPassword(username, password);
    }

    public User register(String username, String password, String firstName, String lastName) {
        System.out.println("register: " + username + " " + password + " " + firstName + " " + lastName);
        for(User user:userList) {
            if(user.getUsername().equals(username)) {
                throw new RuntimeException("User already exists");
            }
        }
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);

        userList.add(user);

        //TODO: uncomment this
        //usersRepository.add(user);
        //user = usersRepository.findByUsernameAndPassword(username, password);

        return user;
    }
}
