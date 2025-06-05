package org.example.deokbook.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.book.entity.Book;
import org.example.deokbook.domain.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당된 책을 찾을 수 없습니다."));
    }
}
