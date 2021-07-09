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
public class ViewPost {

    private final PostService postService;
    private final CommentService commentService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public ViewPost(PostService postService, CommentService commentService, CategoryService categoryService, UserService userService) {
        this.postService = postService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/posts")
    public String getPage(Model model) {

        model.addAttribute("catList" , categoryService.getList());

        return "allPosts";
    }

    @GetMapping(path = "/post")
    public String getForm(@RequestParam(name = "id" , required = false) Integer id , Model model) {
        Post post;

        if (id != null) {
            post = postService.getId(id).orElse(null);
        } else {
            post = new Post();
        }

        model.addAttribute("post" , post);
        return "writePost";
    }

    @GetMapping(path = "/write")
    public String getWritePage(Model model , @RequestParam(name = "id" , required = false) Integer id) {
        Post post;

        if (id == null) {
            post = new Post();
        } else {
            Optional<Post> opt = postService.getId(id);
            if (opt.isEmpty()) {
                return "redirect:/error";
            } else {
                post = opt.get();
            }
        }

        model.addAttribute("categories" , categoryService.getList());
        model.addAttribute("post" , post);

        return "writePost";
    }

    @PostMapping(path = "/write")
    public String postWrite(@ModelAttribute @Valid Post post , BindingResult bindResult , Model model) {
        if (bindResult.hasErrors()) {
            model.addAttribute("categories" , categoryService.getList());
            return "writePost";
        }

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String uname = ((UserDetails) principal).getUsername();
            User user = userService.getByName(uname);

            if (user == null) {
                return "redirect:/error";
            } else {
                post.setPostAuthor(user);
                post.setPostSolved(false);
                post.setPostState(ContentState.CS_NORMAL);
                postService.save(post);
            }
        } catch (HttpStatusCodeException e) {
            bindResult.rejectValue(null , String.valueOf(e.getStatusCode().value()) , e.getStatusCode().getReasonPhrase());
            return "writePost";
        }

        // Redirect to post
        return "redirect:/thread?id=" + post.getPostId().toString();
    }

    @GetMapping(path = "/tlock")
    public String getLockThread(@RequestParam(name = "pid") Integer postId) {
        String uname = FoPorUtils.getActiveUserName();

        if (uname != null) {
            User user = userService.getByName(uname);

            if (user.getUserRole().equals(UserRole.ADMIN)) {
                Post post = postService.getId(postId).orElse(null);

                if (post != null) {
                    Boolean state = post.getPostSolved();
                    post.setPostSolved(!state);
                    postService.save(post);

                    return "redirect:/thread?id=" + postId;
                }
            }
        }

        return "redirect:/access_denied";
    }

    @GetMapping(path = "/post" , params = {"pid" , "delete"})
    public String getDeletePost(@RequestParam(name = "pid") Integer postId , @RequestParam(name = "delete") Boolean delete) {
        String uname = FoPorUtils.getActiveUserName();

        if (uname != null && postId != null && delete != null && delete) {
            User user = userService.getByName(uname);

            if (user.getUserRole().equals(UserRole.ADMIN)) {
                Post post = postService.getId(postId).orElse(null);
//                List<Comment> comments = post == null ? null : post.getPostComments();
//
//                if (comments != null) {
//                    for (var c : comments) {
//                        c.removeCommentPost(post);
//                        commentService.save(c);
//                    }
//
//                    post.setPostComments(null);
//                }
//
//                postService.save(post);

                if (post != null) {
                    for (var c : commentService.getForPost(post)) {
                        commentService.deleteId(c.getCommentId());
                    }
                    postService.deleteId(postId);
                    return "redirect:/posts";
                } else {
                    return "redirect:/not_found";
                }
            }
        }

        return "redirect:/bad_request";
    }

    @GetMapping(path = "/thread")
    public String getThreadPage(@RequestParam Integer id , Model model) {
        Post post = postService.getId(id).get();

        String uname = FoPorUtils.getActiveUserName();
        User user = uname == null ? null : userService.getByName(uname);
        UserRole urole = user == null ? UserRole.NONE : user.getUserRole();

        var comments = commentService.getCommentForPost(post);
        var followers = post.getPostFollowedBy();
        var followStatus = user != null && !followers.isEmpty() && followers.contains(user);

        model.addAttribute("post_author_uid" , post.getPostAuthor().getUserId());
        model.addAttribute("urole" , urole);
        model.addAttribute("followStatus" , followStatus);
        model.addAttribute("post" , post);
        model.addAttribute("comments" , comments);
        model.addAttribute("commentsNum" , comments.size());

        return "viewThread";
    }
}
