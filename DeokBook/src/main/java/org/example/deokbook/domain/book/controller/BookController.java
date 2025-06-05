package org.example.deokbook.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.book.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
}
