package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.fopor.serwis.service.CommentService;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

@Controller
public class ViewIndex {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public ViewIndex(PostService postService, CommentService commentService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping("/")
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getPage(Model model) {

        model.addAttribute("postsNum" , postService.countAll());
        model.addAttribute("postsNum2" , postService.countResolved());
        model.addAttribute("commentsNum" , commentService.countAll());
        model.addAttribute("usersNum" , userService.countAll());

        model.addAttribute("topPosts" , postService.getIndexTopResults());

        return "index";
    }
}
