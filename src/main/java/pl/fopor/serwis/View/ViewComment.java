package pl.fopor.serwis.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import pl.fopor.serwis.model.Comment;
import pl.fopor.serwis.model.ContentState;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.CommentService;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;

@Controller
public class ViewComment {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public ViewComment(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/reply")
    public String privateGetReplyPage(@RequestParam(name = "pid" , required = true) Integer pid , Model model) {

        if (pid == null || pid < 1) {
            return "redirect:/error";
        }

        try {
            Post post = postService.getId(pid).orElseThrow();
            Comment comment = new Comment();
            comment.setCommentPost(post);

            model.addAttribute("comment" , comment);
        } catch (Exception err) {
            return "redirect:/error";
        }
        return "writeReply";
    }

    @PostMapping(path = "/reply")
    public String postWrite(@ModelAttribute @Valid Comment comment , @RequestParam(name = "pid" , required = true) Integer pid, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return "writeReply";
        } else {
            try {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String uname = ((UserDetails) principal).getUsername();
                User user = userService.getByName(uname);
                Post post = postService.getId(pid).orElseThrow();

                if (user == null) {
                    return "redirect:/error";
                } else {
                    comment.setCommentAuthor(user);
                    comment.setCommentPost(post);
                    commentService.save(comment);
                }
            } catch (HttpStatusCodeException e) {
                bindResult.rejectValue(null , String.valueOf(e.getStatusCode().value()) , e.getStatusCode().getReasonPhrase());
                return "writeReply";
            }

            // Redirect to post
            return "redirect:/thread?id=" + comment.getCommentPost().getPostId().toString();
        }
    }
}
