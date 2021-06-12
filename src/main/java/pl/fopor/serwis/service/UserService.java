package pl.fopor.serwis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements ServiceTpl<User> {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getId(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    @Transactional
    public boolean deleteId(Integer id) {
        Optional<User> objectToDelete = userRepository.findById(id);

        if (objectToDelete.isPresent()) {
            userRepository.delete(objectToDelete.get());
            return true;
        }

        return false;
    }

    public User getByName(String name) {
        return userRepository.findByUserMail(name);
    }
}
