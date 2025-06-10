package org.example.deokbook.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.book.entity.Book;
import org.example.deokbook.domain.book.repository.BookRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    
    // 책 목록
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
    
    // 책 저장
    public Book saveBook(Book book) {
        book.setAvailable('y');
        return bookRepository.save(book);
    }
    
    // 책 삭제
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    // 책 수정
    public Book updateBook(Long id, Book book) {
        Book books = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));

        books.updateBook(book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublishedDate(),
                book.getPrice(),
                book.getStatus());

        return bookRepository.save(books);
    }
    
    // 검색
    public List<Book> search(String keyword, String title, String author, String publisher) {
        Specification<Book> spec = Specification.where(null);

        if (title != null && !title.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("title"), "%" + title.toLowerCase() + "%"));
        }

        if (author != null && !author.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("author"), "%" + author.toLowerCase() + "%"));
        }

        if (publisher != null && !author.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("publisher"), "%" + publisher.toLowerCase() + "%"));
        }

        return bookRepository.findAll(spec);
    }
    
    // 정렬
    public List<Book> sortBooks(List<Book> books, String sortBy, String sortDir) {
        Comparator<Book> comparator = null;

        switch (sortBy) {
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
                break;
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "publisher":
                comparator = Comparator.comparing(Book::getPublisher);
                break;
        }

        if("DESC".equalsIgnoreCase(sortDir)) {
            comparator = comparator.reversed();
        }

        return books.stream()
                .sorted(comparator)
                .toList();
    }
    
    // 책 대출 가능한지
    public boolean isAvailable(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당된 책을 찾을 수 없습니다."));

        return book.isAvailable();
    }
}
