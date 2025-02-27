package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.services.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/book/{bookId}/comment")
    public String findCommentsByBookId(@PathVariable String bookId, Model model) {
        var comments = commentService.findByBookId(bookId);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", bookId);
        return "book_comments";
    }

    @DeleteMapping("/book/{bookId}/comment/{id}")
    public String deleteComment(@PathVariable String id, @PathVariable String bookId, Model model) {
        commentService.deleteById(id);
        model.addAttribute("bookId", bookId);
        return "redirect:/book/{bookId}/comment";
    }

    @PostMapping("/book/{bookId}/comment/{id}")
    public String updateComment(@PathVariable String id, @PathVariable String bookId, String text, Model model) {
        commentService.update(id, text, bookId);
        model.addAttribute("bookId", bookId);
        return "redirect:/book/{bookId}/comment";
    }

    @GetMapping("/book/{bookId}/comment/{id}")
    public String findCommentById(@PathVariable String id,  @PathVariable String bookId, Model model) {
        var comment = commentService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Комментарий не найдена"));
        model.addAttribute("comment", comment);
        model.addAttribute("bookId", bookId);
        return "comment_edit";
    }

    @GetMapping("/book/{bookId}/comment/add")
    public String openAddComment(@PathVariable String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "comment_add";
    }

    @PostMapping("/book/{bookId}/comment/add")
    public String insertComment(@PathVariable String bookId, String text, Model model) {
        commentService.insert(text, bookId);
        model.addAttribute("bookId", bookId);
        return "redirect:/book/{bookId}/comment";
    }
}
