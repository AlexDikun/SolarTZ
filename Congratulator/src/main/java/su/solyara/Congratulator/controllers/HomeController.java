package su.solyara.Congratulator.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import su.solyara.Congratulator.DTO.BirthdayEventDTO;
import su.solyara.Congratulator.service.BirthdayService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

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

}
