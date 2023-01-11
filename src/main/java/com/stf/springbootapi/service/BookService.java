package com.stf.springbootapi.service;

import com.stf.springbootapi.domain.Book;
import com.stf.springbootapi.domain.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {

    Book getBookById(long id);

    List<Book> findAllBooks();

    Book createBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);


}
