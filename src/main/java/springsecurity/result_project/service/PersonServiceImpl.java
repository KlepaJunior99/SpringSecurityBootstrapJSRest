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

    public PersonServiceImpl(PersonRepository personRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void save(Person person) {
        Person personFromDB = personRepository.findByUsername(person.getUsername());
        if (personFromDB == null) {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            personRepository.save(person);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Person show(Long id) {
        return personRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public void update(Person person) {
        if (personRepository.findByUsername(person.getUsername()) != null && !personRepository.findByUsername(person.getUsername()).getId().equals(person.getId())) {
            throw new InvalidParameterException("Пользователь с таким email уже существует: "
                    + person.getUsername());
        }
        if (person.getPassword().isEmpty()) {
            person.setPassword(personRepository.findById(person.getId()).get().getPassword());
        } else {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
        }
        personRepository.saveAndFlush(person);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (personRepository.findById(id).isPresent()) {
            personRepository.deleteById(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Person> showAll() {
        return personRepository.findAll();
    }


    @Override
    public Person loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
        }
        return person;
    }
}
