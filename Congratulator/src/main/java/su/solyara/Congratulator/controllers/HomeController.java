package su.solyara.Congratulator.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import su.solyara.Congratulator.DTO.BirthdayEventDTO;
import su.solyara.Congratulator.DTO.PersonDTO;
import su.solyara.Congratulator.service.BirthdayService;
import su.solyara.Congratulator.service.PersonService;
import su.solyara.Congratulator.domain.positions.Position;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class HomeController {

    @Autowired
    private PersonService personService;
    
    @Autowired
    private BirthdayService birthdayService;
    
    @GetMapping("/")
    public String showHome(Model model) {
        LocalDate today = LocalDate.now();  
        int days = 7;  

        List<BirthdayEventDTO> birthdayEvents = birthdayService.getUpcomingBirthdays(today, days);

        model.addAttribute("birthdays", birthdayEvents);
        model.addAttribute("periodTitle", "Дни рождения на неделю");

        return "home";
    }

    @GetMapping("/all")
    public String showAllBirthdays(Model model) {
        List<BirthdayEventDTO> allBirthdays = birthdayService.getAllBirthdays();
        model.addAttribute("birthdays", allBirthdays);
        return "all";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("person", new PersonDTO());
        model.addAttribute("positions", Position.values());
        return "add";
    }

    @PostMapping("/add")
    public String addPerson(
        @ModelAttribute("person") PersonDTO personDTO,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        personService.createPerson(personDTO, file);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PersonDTO person = personService.getPersonById(id);
        model.addAttribute("person", person);
        model.addAttribute("positions", Position.values());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String editPerson(
        @PathVariable Long id,
        @ModelAttribute("person") PersonDTO personDTO,
        @RequestParam("file") MultipartFile file
    ) throws IOException {
        personService.updatePerson(id, personDTO, file);
        return "redirect:/all";
    }

    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return "redirect:/all";
    }

}
