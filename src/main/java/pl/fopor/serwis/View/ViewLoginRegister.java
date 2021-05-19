package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.UserService;

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

}
