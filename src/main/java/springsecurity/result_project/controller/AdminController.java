package springsecurity.result_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springsecurity.result_project.model.Person;
import springsecurity.result_project.service.PersonService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonService personService;

    public AdminController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("authUser", personService.loadUserByUsername(principal.getName()));
        model.addAttribute("userList", personService.showAll());
        model.addAttribute("newUser", new Person());
        return "admin";
    }

    @PostMapping("/save")
    public String createNewPerson(@ModelAttribute("newUser") Person person) {
        personService.save(person);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String getUpdatePersonForm(@ModelAttribute("user") Person person) {
        personService.update(person);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        personService.delete(id);
        return "redirect:/admin";
    }
}
