package springsecurity.result_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springsecurity.result_project.model.Person;
import springsecurity.result_project.service.PersonService;

import java.util.List;

@RestController
public class AdminRestController {

    private final PersonService personService;

    public AdminRestController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/main")
    public ModelAndView getMainPage() {
        return new ModelAndView("main");
    }

    @GetMapping("api/admin")
    public ResponseEntity<List<Person>> getInfoUsersList() {
        List<Person> userList = personService.getAllUsers();
        if (userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        }
        return new ResponseEntity<>(userList, HttpStatus.OK); // 200
    }

    @GetMapping("api/admin/{id}")
    public ResponseEntity<Person> getUserById(@PathVariable long id) {
        Person user = personService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
        return new ResponseEntity<>(user, HttpStatus.OK); // 200
    }

    @PostMapping("api/admin")
    public ResponseEntity<Person>  getSaveUserForm(@RequestBody Person user) {
        if (personService.addUser(user)) {
            return new ResponseEntity<>(user, HttpStatus.CREATED); // 201
        }
        return new ResponseEntity<>(user, HttpStatus.CONFLICT); // 400
    }

    @PutMapping("api/admin")
    public ResponseEntity<Person> getUpdateUserForm(@RequestBody Person user) {
        if (personService.updateUser(user)) {
            return new ResponseEntity<>(user, HttpStatus.OK); // 200
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT); // 400
    }

    @DeleteMapping("api/admin/{id}")
    public ResponseEntity<Long>  getRemoveUserForm(@PathVariable long id) {
        if (personService.deleteUserById(id)) {
            return new ResponseEntity<>(id, HttpStatus.OK); // 200
        }
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND); // 404
    }
}
