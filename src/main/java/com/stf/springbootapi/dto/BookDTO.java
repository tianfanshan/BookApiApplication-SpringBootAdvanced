package com.stf.springbootapi.dto;

import com.stf.springbootapi.domain.Book;
import com.stf.springbootapi.util.CustomBeanUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

public class BookDTO {

    @NotBlank
    private String author;
    @Length(max = 20)
    private String description;
    @NotBlank
    private String name;
    @NotNull
    private int status;

    public BookDTO() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * 转换传输对象
     *
     * @param book
     */
    public void convertToBook(Book book) {
        new BookConverter().convert(this, book);
    }

    public Book convertToBook() {
        return new BookConverter().convert(this);
    }

    private class BookConverter implements Convert<BookDTO, Book> {

        @Override
        public Book convert(BookDTO bookDTO, Book book) {
            String[] nullPropertyNames = CustomBeanUtils.getNullPropertyNames(bookDTO);
            BeanUtils.copyProperties(bookDTO, book, nullPropertyNames);
            return book;
        }

        @Override
        public Book convert(BookDTO bookDTO) {
            Book book = new Book();
            BeanUtils.copyProperties(bookDTO, book);
            return book;
        }
    }
}
