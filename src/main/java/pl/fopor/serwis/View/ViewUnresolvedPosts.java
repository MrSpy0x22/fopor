package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

import java.util.List;

@Controller
public class ViewUnresolvedPosts {

    private final PostService postService;

    @Autowired
    public ViewUnresolvedPosts(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/unresolved")
    public String getPage(Model model ) {

        return "unresolvedPosts";
    }
}
