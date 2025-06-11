package org.example.deokbook.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.Repository.LoanRepository;
import org.example.deokbook.dto.BookDto;
import org.example.deokbook.dto.BookSearchRequest;
import org.example.deokbook.entity.Book;
import org.example.deokbook.Repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public Page<BookDto> searchBooks(BookSearchRequest request) {
        Sort sort = Sort.by(
                "desc".equalsIgnoreCase(request.getSortDirection()) ?
                        Sort.Direction.DESC : Sort.Direction.ASC,
                request.getSortBy()
        );

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize(), sort);

        Page<Book> books = bookRepository.findBySearchCriteria(
                request.getTitle(),
                request.getAuthor(),
                request.getPublisher(),
                pageable
        );

        return books.map(this::convertToDto);
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));
        return convertToDto(book);
    }

    public BookDto createBook(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublisher(bookDto.getPublisher());
        book.setPublishedYear(bookDto.getPublishYear());
        book.setPrice(bookDto.getPrice());
        book.setAvailable(true);

        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    public BookDto updateBook(Long id, BookDto bookDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setPublishedYear(bookDTO.getPublishYear());
        book.setPrice(bookDTO.getPrice());

        Book savedBook = bookRepository.save(book);
        return convertToDto(savedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BookDto convertToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setPublishYear(book.getPublishedYear());
        dto.setPrice(book.getPrice());
        dto.setAvailable(book.isAvailable());

        if(!book.isAvailable()) {
            boolean isOverdue = loanRepository.findByBookIdAndReturnedFalse(book.getId())
                    .stream()
                    .anyMatch(loan -> loan.getDueDate().isBefore(LocalDateTime.now()));
            dto.setOverdue(isOverdue);
        }

        return dto;
    }
}
