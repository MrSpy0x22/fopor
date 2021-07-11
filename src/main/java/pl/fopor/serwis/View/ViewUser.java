package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import pl.fopor.serwis.Utils.FoPorDataUtils;
import pl.fopor.serwis.Utils.FoPorUtils;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.model.UserRole;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.UserService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class ViewUser {

    private final UserService userService;

    @Autowired
    public ViewUser(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getPage(Model model ) {
        List<User> users = userService.getAll();

        var uname = FoPorUtils.getActiveUserName();
        var user = uname == null ? null : userService.getByName(uname);

        model.addAttribute("admin" , user == null ? null : user.getUserRole().equals(UserRole.ADMIN));
        return "allUsers";
    }

    @GetMapping(path = "/user")
    public String getForm(@RequestParam(name = "id" , required = false) Integer id , Model model) {
        User user;

        if (id != null) {
            user = userService.getId(id).orElse(null);

            if (user == null) {
                return "messages/not_found";
            }
        } else {
            user = new User();
        }

        model.addAttribute("user" , user);
        return "popups/p_userForm";
    }


    @PostMapping(path = "/user")
    public String postCategory(@ModelAttribute @Valid User target , BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "popups/p_userForm";
        } else {
            try {
                String uname = FoPorUtils.getActiveUserName();
                User user = uname == null ? null : userService.getByName(uname);

                if (user == null) {
                    return "redirect:/access_denied";
                } else {
                    User originalUser = target == null ? null : userService.getByName(target.getUserName());

                    if (target != null && target.getUserPassword().length() > 0) {
                        target.setUserPassword(new BCryptPasswordEncoder().encode(target.getUserPassword()));
                    }
                    if (originalUser != null && target.getUserPassword().length() == 0) {
                        target.setUserPassword(originalUser.getUserPassword());
                    }

                    userService.save(target);
                }
            } catch (HttpStatusCodeException e) {
                bindResult.rejectValue("error" , String.valueOf(e.getStatusCode().value()) , e.getStatusCode().getReasonPhrase());
                System.out.println("Exception catched: " + e.getMessage());
                return "popups/p_userForm";
            }

            return "redirect:/success";
        }
    }

    @PostMapping(path = "/user" , params = "delete")
    public String deleteCategory(@ModelAttribute User target) {
        if (target == null) {
            return "redirect:/not_found";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uname = ((UserDetails) principal).getUsername();
        User user = userService.getByName(uname);

        if (user.getUserRole() == UserRole.ADMIN) {
            userService.deleteId(target.getUserId());
            return "redirect:/deleted";
        } else {
            return "redirect:/access_denied";
        }
    }
}
