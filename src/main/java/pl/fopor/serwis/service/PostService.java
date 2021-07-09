package pl.fopor.serwis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.fopor.serwis.Utils.FoPorSearchResultModel;
import pl.fopor.serwis.model.Post;
import pl.fopor.serwis.model.User;
import pl.fopor.serwis.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public Page<Post> getPreviewForAll(Boolean solved , Pageable pageable) {
        return postRepository.findPreviewForAll(solved , pageable);
    }

    public Page<Post> getPreviewForCategory(Boolean solved , Integer category , Pageable pageable) {
        return postRepository.findPreviewForCategory(solved , category , pageable);
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

    public Page<FoPorSearchResultModel> getPostSearchResult(String text , Pageable pageable) {
        var results = postRepository.findAllBySearchCriteria(text , pageable);
        var result = new ArrayList<FoPorSearchResultModel>();

        for (var r : results.getContent()) {
            result.add(new FoPorSearchResultModel(r.getPostTitle() , "Post" , r.getPostCreationTime()));
        }

        return new PageImpl<>(result , PageRequest.of(pageable.getPageNumber(), 15) , result.size());
    }
}
