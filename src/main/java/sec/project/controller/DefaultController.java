package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String defaultMapping() {
        return "redirect:/homepage";
    }
    
    /*@GetMapping("/error")
    public String error() {
        return "homepage";
    }*/

    @GetMapping("/homepage")
    public String home() {
        return "homepage";
    }
    
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/homepage";
    }

    
}
