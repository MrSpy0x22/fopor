package pl.fopor.serwis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fopor.serwis.model.Comment;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements ServiceTpl<Comment> {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> getId(Integer id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Page<Comment> getPage(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment save(Comment object) {
        return commentRepository.save(object);
    }

    @Override
    public boolean deleteId(Integer id) {
        Optional<Comment> objectToDelete = commentRepository.findById(id);

        if (objectToDelete.isPresent()) {
            commentRepository.delete(objectToDelete.get());
            return true;
        }

        return false;
    }

    public List<Comment> getCommentForPost(Post post) {
        return commentRepository.findByCommentPost(post);
    }
}
