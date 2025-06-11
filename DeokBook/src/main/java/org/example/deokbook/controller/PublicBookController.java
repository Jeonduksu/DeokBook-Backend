package org.example.deokbook.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.dto.BookDto;
import org.example.deokbook.dto.BookSearchRequest;
import org.example.deokbook.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/book")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PublicBookController {

    private final BookService bookService;

    @PostMapping("/search")
    public ResponseEntity<Page<BookDto>> searchBook(@RequestBody BookSearchRequest bookSearchRequest) {
        Page<BookDto> books = bookService.searchBooks(bookSearchRequest);
        return ResponseEntity.ok(books);
    }

}
