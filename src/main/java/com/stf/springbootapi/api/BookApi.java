package com.stf.springbootapi.api;

import com.stf.springbootapi.domain.Book;
import com.stf.springbootapi.dto.BookDTO;
import com.stf.springbootapi.exception.InvalidRequestException;
import com.stf.springbootapi.exception.NotFoundException;
import com.stf.springbootapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookApi {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = bookService.findAllBooks();
        if (books.isEmpty()) {
            throw new NotFoundException("Books not found");
        }
        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> findBookById(@PathVariable long id) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new NotFoundException(String.format("Book with id %s not found", id));
        }
        return new ResponseEntity<Object>(book, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<?> createBook(@Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        Book book1 = bookService.createBook(bookDTO.convertToBook());
        return new ResponseEntity<Object>(book1, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateById(@PathVariable long id, @Valid @RequestBody BookDTO bookDTO, BindingResult bindingResult) {
        Book currentBook = bookService.getBookById(id);
        if (currentBook == null) {
            throw new NotFoundException(String.format("Book by id %s not found", id));
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid parameter", bindingResult);
        }
        bookDTO.convertToBook(currentBook);
        Book book = bookService.updateBook(currentBook);
        return new ResponseEntity<Object>(book, HttpStatus.OK);
    }


    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id) {
        Book currentBook = bookService.getBookById(id);
        if (currentBook != null) {
            bookService.deleteBookById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
