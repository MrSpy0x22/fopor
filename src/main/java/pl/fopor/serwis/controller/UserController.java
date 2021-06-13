package pl.fopor.serwis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController implements ControllerTpl<User> {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/{id}")
    @Override
    public User getId(@PathVariable("id") Integer id) {
        return userService.getId(id).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping
    @Override
    public Page<User> getPageOf(Pageable pageable) {
        return userService.getPage(pageable);
    }

    @PostMapping
    @Override
    public User postObject(@Valid @RequestBody User object) {
        return userService.save(object);
    }

    @PutMapping("/{id}")
    @Override
    public User putObject(@RequestBody User object, @PathVariable("id") Integer id) {
        Optional<User> optional = userService.getId(id);

        return optional.map(userService::save).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/{id}")
    @Override
    public boolean deleteId(@PathVariable("id") Integer id) {
        return userService.deleteId(id);
    }

    @PostMapping("/follow/{pid}")
    public ResponseEntity<Void> setFollowerForPostId(@PathVariable Integer pid) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uname = ((UserDetails) principal).getUsername();
        User user = userService.getByName(uname);
        Post post = postService.getId(pid).orElseGet(Post::new);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (post.getPostId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            user.addFollowedPost(post);
            userService.save(user);
            postService.save(post);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/unfollow/{pid}")
    public ResponseEntity<Void> unfollowPost(@PathVariable Integer pid) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uname = ((UserDetails) principal).getUsername();
        User user = userService.getByName(uname);
        Post post = postService.getId(pid).orElseGet(Post::new);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else if (post.getPostId() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            user.removeFollowedPost(post);
            userService.save(user);
            postService.save(post);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
