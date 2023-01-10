package springsecurity.result_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springsecurity.result_project.service.PersonService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String showPersonInfo(Model model, Principal principal) {
        model.addAttribute("authUser", personService.loadUserByUsername(principal.getName()));
        return "user";
    }
}
