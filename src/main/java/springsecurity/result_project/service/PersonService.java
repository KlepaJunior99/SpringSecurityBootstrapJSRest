package springsecurity.result_project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import springsecurity.result_project.model.Person;

import java.util.List;

public interface PersonService extends UserDetailsService {

    boolean addUser(Person user);

    Person getUserById(Long id);

    boolean updateUser(Person user);

    boolean deleteUserById(Long id);

    List<Person> getAllUsers();

    Person loadUserByUsername (String name);
}
