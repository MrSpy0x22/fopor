package pl.fopor.serwis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.fopor.serwis.Utils.FoPorSearchResultModel;
import pl.fopor.serwis.service.CategoryService;
import pl.fopor.serwis.service.CommentService;
import pl.fopor.serwis.service.PostService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final CategoryService categoryService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public SearchController(CategoryService categoryService, PostService postService, CommentService commentService) {
        this.categoryService = categoryService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public Page<FoPorSearchResultModel> getSearchResult(@RequestParam("text") String text , Pageable pageable) {
        var categories = categoryService.getCategorySearchResult(text , pageable).getContent();
        var posts = postService.getPostSearchResult(text , pageable).getContent();
        var comments = commentService.getCommentSearchResult(text , pageable).getContent();

        var list = List.of(categories , posts , comments).stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(FoPorSearchResultModel::getDatetime))
                .collect(Collectors.toList());

        return new PageImpl<>(list , pageable , list.size());
    }
}
