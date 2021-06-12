package pl.fopor.serwis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.service.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController implements ControllerTpl<Post> {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
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
