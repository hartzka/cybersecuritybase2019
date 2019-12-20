package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String defaultMapping() {
        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public String home() {
        return "homepage";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/homepage";
    }

    
}
