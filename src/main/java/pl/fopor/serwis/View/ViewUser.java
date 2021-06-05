package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.fopor.serwis.model.Category;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.UserService;

import java.util.List;

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

        //model.addAttribute("catList" , users);
        return "allUsers";
    }

    @GetMapping(path = "/user")
    public String getForm(@RequestParam(name = "id" , required = false) Integer id , Model model) {
        User user;

        if (id != null) {
            user = userService.getId(id).orElse(null);
        } else {
            user = new User();
        }

        model.addAttribute("user" , user);
        return "popups/p_usrForm";
    }
}
