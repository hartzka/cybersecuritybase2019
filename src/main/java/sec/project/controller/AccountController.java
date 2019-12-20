package sec.project.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/accounts")
    public String list(Model model) {
        return "accounts";
    }

    @PostMapping("/accounts")
    public String add(@Valid @ModelAttribute Account a, BindingResult bindingResult, Model model) {
        if (accountRepository.findByUsername(a.getUsername()) != null) {
            model.addAttribute("reserved", true);
            return "accounts";
        }
        if (bindingResult.hasErrors()) {
            return "accounts";
        }
        Account account = new Account();
        account.setUsername(a.getUsername());
        account.setPassword(a.getPassword());

        accountRepository.save(account);
        model.addAttribute("created", true);
        return "accounts";
    }

    @PostMapping("/change/{id}")
    public String change(@PathVariable Long id, @RequestParam String password) {
        if (accountRepository.findById(id) != null) {
            Account account = accountRepository.findById(id);
            account.setPassword(password);
            accountRepository.save(account);
        }
        return "redirect:/info/" + id;
    }
}
