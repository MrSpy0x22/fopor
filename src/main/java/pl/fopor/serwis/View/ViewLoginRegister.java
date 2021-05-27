package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;

@Controller
public class ViewLoginRegister {

    private final UserService userService;

    @Autowired
    public ViewLoginRegister(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model , Authentication auth) {
        if (auth != null) {
            return "redirect:/index";
        } else {
            model.addAttribute("user" , new User());

            return "userLogin";
        }
    }

    @GetMapping("/remind")
    public String getRemindPage(Model model) {
        return "userRemind";
    }

    @PostMapping("/remind")
    public String postRemindPage() {
        return "redirect:/remind(todo=true)";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user" , new User());

        return "userRegister";
    }

    @PostMapping("/register")
    public String postRegisterPage(@Valid User user , BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "userRegister";
        } else {
            String userPasswd = user.getUserPassword();
            user.setUserPassword(new BCryptPasswordEncoder().encode(userPasswd));
            userService.save(user);

            return "redirect:/index";
        }
    }

}
