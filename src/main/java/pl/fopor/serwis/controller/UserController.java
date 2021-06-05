package pl.fopor.serwis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.service.UserService;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController implements ControllerTpl<User> {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
}
