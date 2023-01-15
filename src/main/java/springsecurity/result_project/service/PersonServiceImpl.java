package springsecurity.result_project.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springsecurity.result_project.model.Person;
import springsecurity.result_project.repository.PersonRepository;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.personRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean addUser(Person person) {
        Person userFromDB = personRepository.findByUsername(person.getUsername());
        if (userFromDB != null) {
            return false;
        }
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Person getUserById(Long id) {
        return personRepository.findById(id).get();
    }

    @Override
    @Transactional
    public boolean updateUser(Person person) throws InvalidParameterException {
        if (personRepository.findByUsername(person.getUsername()) != null &&
                !personRepository.findByUsername(person.getUsername()).getId().equals(person.getId())) {
            return false;
        }
        if (person.getPassword().isEmpty()) {
            person.setPassword(personRepository.findById(person.getId()).get().getPassword());
        } else {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
        }
        personRepository.save(person);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUserById(Long id) {
        if (personRepository.findById(id).isEmpty()) {
            return false;
        }
        personRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> getAllUsers() {
        return personRepository.findAll();
    }

    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = personRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Person '%s' not found ", username));
        }
        return user;
    }
}
