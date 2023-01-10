package springsecurity.result_project.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import springsecurity.result_project.model.Person;

import java.util.List;

public interface PersonService extends UserDetailsService {

    void save(Person person);

    Person show(Long id);

    void update(Person person);

    void delete(Long id);

    List<Person> showAll();

    Person loadUserByUsername(String name);
}
