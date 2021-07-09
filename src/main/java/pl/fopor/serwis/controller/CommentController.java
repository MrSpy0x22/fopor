package pl.fopor.serwis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fopor.serwis.model.Comment;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.service.CommentService;
import pl.fopor.serwis.service.PostService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping("/for/{id}")
    public Page<Comment> getCommentsPageFOrPostId(@PathVariable("id") Integer postId , Pageable pageable) {
        Post post = postService.getId(postId).orElse(null);
        return post == null ? null : commentService.getCommentForPost(post , pageable);
    }
}
