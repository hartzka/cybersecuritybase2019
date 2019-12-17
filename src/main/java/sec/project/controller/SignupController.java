package sec.project.controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.db.DataBaseManager;
import sec.project.domain.Account;
import sec.project.domain.Signup;
import sec.project.repository.AccountRepository;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    private Signup currentSignup;
    private DataBaseManager dbm;

    @Autowired
    private SignupRepository signupRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    public SignupController() throws SQLException {
        dbm = new DataBaseManager();
        dbm.setup("jdbc:sqlite:data.db");
        dbm.createTablesIfAbsent();
    }

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account acc = accountRepository.findByUsername(username);
        model.addAttribute("account", acc);
        return "form";
    }
    
    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public String loadSignups(Model model) {
        return "done";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Model model, @RequestParam String name, @RequestParam String address) throws SQLException {
        Signup signup = new Signup(name, address);
        signupRepository.save(signup);
        PreparedStatement psm = dbm.openConnection().prepareStatement("INSERT INTO Signup (name, address) VALUES (?, ?);");
        psm.setString(1, name);
        psm.setString(2, address);
        psm.executeUpdate();
        currentSignup = signup;
        model.addAttribute("signup", signup);
        return "done";
    }

    @RequestMapping(value = "/form/search", method = RequestMethod.POST)
    public String searchByName(Model model, @RequestParam String name) {
        List<Signup> signups = new ArrayList();
        try {
            ResultSet rs = dbm.openConnection().prepareStatement("SELECT * FROM Signup WHERE name='" + name + "';").executeQuery();
            while (rs.next()) {
                Signup signup = new Signup();
                signup.setName(rs.getString("name"));
                signup.setAddress(rs.getString("address"));
                signups.add(signup);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        //model.addAttribute("signup", currentSignup);
        model.addAttribute("signups", signups);
        return "done";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/info/{id}")
    public String info(Model model, @PathVariable Long id) {
        Account acc = accountRepository.findById(id);
        if (acc == null) return "redirect:/error";
        model.addAttribute("account", acc);
        return "info";
    }
    
    @RequestMapping("/signups/{name}")
    public String signup(Model model, @PathVariable String name) {
        List<Signup> signups = signupRepository.findByName(name);
        model.addAttribute("signups", signups);
        return "signups";
    }

}
