package pl.fopor.serwis.View;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import pl.fopor.serwis.Utils.FoPorUtils;
import pl.fopor.serwis.model.*;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.CommentService;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ViewBookmarks {

    private final PostService postService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ViewBookmarks(PostService postService, CommentService commentService, CategoryService categoryService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/saved")
    public String getPage(Model model) {

        var uname = FoPorUtils.getActiveUserName();

        if (uname == null) {
            return "redirect:/login";
        }

        User user = userService.getByName(uname);

        model.addAttribute("uid" , user.getUserId());

        return "allBookmarks";
    }
}
