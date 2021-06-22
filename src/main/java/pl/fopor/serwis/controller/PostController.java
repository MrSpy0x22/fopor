package pl.fopor.serwis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.PostService;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
public class PostController implements ControllerTpl<Post> {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Override
    public Post getId(@PathVariable("id") Integer id) {
        return postService.getId(id).orElse(null);
    }

    @GetMapping
    @Override
    public Page<Post> getPageOf(Pageable pageable) {
        return postService.getPage(pageable);
    }

    @GetMapping("/preview")
    public Page<Post> getPreviewPage(Pageable pageable) {
        var page = postService.getPage(pageable);
        page.getContent()
                .stream()
                .filter(p -> p.getPostContent().length() > 128)
                .forEach(p -> p.setPostContent(p.getPostContent().substring(0 , 128)));
        return page;
    }

    @PostMapping
    @Override
    public Post postObject(@Valid @RequestBody Post object) {
        return postService.save(object);
    }

    @PutMapping("/{id}")
    @Override
    public Post putObject(Post object, @PathVariable("id") Integer id) {
        Optional<Post> optional = postService.getId(id);

        return optional.map(postService::save).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/{id}")
    @Override
    public boolean deleteId(@PathVariable("id") Integer id) {
        return postService.deleteId(id);
    }

    @GetMapping("/unresolved")
    public Page<Post> findByPostSolvedFalse(Pageable pageable) {
        return postService.findByPostSolvedFalse(pageable);
    }

}
