package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.utils.GenreUtils;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/book")
    public String findAllBooks(Model model) {
        var books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book/{id}")
    public String editPage(@PathVariable String id, Model model) {
        var book = bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Книга не найдена"));
        var authors = authorService.findAll();
        var genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("book", book);
        return "book_edit";
    }

    @PostMapping("/book/{id}")
    public String updateBook(@PathVariable String id, String title, String authorId, String genresIds) {
        var genres = GenreUtils.parseGenres(genresIds);
        bookService.update(id, title, authorId, genres);
        return "redirect:/book";
    }

    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
        return "redirect:/book";
    }

    @GetMapping("/book/add")
    public String openAddBookPage(Model model) {
        var authors = authorService.findAll();
        var genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book_add";
    }

    @PostMapping("/book/add")
    public String insertBook(String title, String authorId, String genresIds) {
        var genres = GenreUtils.parseGenres(genresIds);
        bookService.insert(title, authorId, genres);
        return "redirect:/book";
    }
}
