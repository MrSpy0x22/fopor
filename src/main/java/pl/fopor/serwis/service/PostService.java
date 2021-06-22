package pl.fopor.serwis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements ServiceTpl<Post> {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> getId(Integer id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Page<Post> getPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Post save(Post object) {
        return postRepository.save(object);
    }

    @Override
    @Transactional
    public boolean deleteId(Integer id) {
        Optional<Post> objectToDelete = postRepository.findById(id);

        if (objectToDelete.isPresent()) {
            postRepository.delete(objectToDelete.get());
            return true;
        }

        return false;
    }

    public Page<Post> findByPostSolvedFalse(Pageable pageable) {
        return postRepository.findByPostSolvedFalse(pageable);
    }

    public List<Post> getByFollower(User user) {
        return postRepository.findPostByPostFollowedBy(user);
    }

    public boolean isFollowedBy(User user , Post post) {
        return postRepository
                .findPostByPostFollowedBy(user)
                .stream()
                .anyMatch(p -> post.getPostId().equals(p.getPostId()));
    }

    public Page<Post> findPostsByPostTitle(String title, Pageable pageable) {
        return postRepository.findByPostTitleContaining(title, pageable);
    }
}
