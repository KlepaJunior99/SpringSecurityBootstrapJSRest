package springsecurity.result_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springsecurity.result_project.service.PersonService;
import springsecurity.result_project.model.Person;

import java.security.Principal;

@RestController
@RequestMapping("api/")
public class UserRestController {

    private final PersonService personService;

    public UserRestController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/user")
    public ResponseEntity<Person> getInfoCurrentUser(Principal principal) {
        return new ResponseEntity<>(personService.loadUserByUsername(principal.getName()), HttpStatus.OK); // 200
    }
}
