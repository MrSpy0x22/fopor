package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.fopor.serwis.service.PostService;

@Controller
public class ViewNewestPosts {

    private final PostService postService;

    @Autowired
    public ViewNewestPosts(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/newest")
    public String getPage(Model model ) {

        return "newestPosts";
    }
}
