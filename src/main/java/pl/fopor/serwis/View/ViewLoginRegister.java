package pl.fopor.serwis.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpStatusCodeException;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;

@Slf4j
@Controller
public class ViewLoginRegister {

    private final UserService userService;

    @Autowired
    public ViewLoginRegister(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model , Authentication auth) {
        String userName = null;

        if (auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            userName = userDetails.getUsername();
        }

        model.addAttribute("user", new User());
        model.addAttribute("mail", userName);

        return "userLogin";
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
    public String postRegisterPage(@ModelAttribute @Valid User user , BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "userRegister";
        } else {
            try {
                String userPasswd = user.getUserPassword();
                user.setUserPassword(new BCryptPasswordEncoder().encode(userPasswd));
                user.setUserRole("USER");
                user.setUserEnabled(true);
                userService.save(user);
                log.warn(user.toString());
            } catch (HttpStatusCodeException e) {
                bindResult.rejectValue(null , String.valueOf(e.getStatusCode().value()) , e.getStatusCode().getReasonPhrase());
                return "userRegister";
            }

            return "redirect:/login";
        }
    }

}
