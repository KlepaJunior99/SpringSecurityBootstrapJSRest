//package springsecirity.result_project.init;
//
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import springsecirity.result_project.model.Person;
//import springsecirity.result_project.model.Role;
//import springsecirity.result_project.repository.RoleRepository;
//import springsecirity.result_project.repository.PersonRepository;
//
//import javax.annotation.PostConstruct;
//import java.util.HashSet;
//import java.util.Set;
//
//@Component
//public class DBInitializer {
//    private final PersonRepository personRepository;
//    private final RoleRepository roleRepository;
//
//    public DBInitializer(PersonRepository personRepository, RoleRepository roleRepository) {
//        this.personRepository = personRepository;
//        this.roleRepository = roleRepository;
//    }
//
//    @PostConstruct
//    public void createTestUsersWithRoles() {
//        Role role1 = new Role("ROLE_ADMIN");
//        Role role2 = new Role("ROLE_USER");
//
//        roleRepository.save(role1);
//        roleRepository.save(role2);
//
//        Person person1 = new Person
//                ("user@mail.ru", "Pavlik", "Ivanov", (byte) 30,
//                        new BCryptPasswordEncoder().encode("user"));
//        Person person2 = new Person
//                ("admin@mail.ru", "Владимир", "Клепиков", (byte) 23,
//                        new BCryptPasswordEncoder().encode("admin"));
//
//        person1.setRoles(new HashSet<>(Set.of(role2)));
//        person2.setRoles(new HashSet<>(Set.of(role1)));
//
//        personRepository.save(person1);
//        personRepository.save(person2);
//
//    }
//}
