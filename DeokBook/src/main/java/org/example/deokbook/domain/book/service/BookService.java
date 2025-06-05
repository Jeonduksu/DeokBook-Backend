package org.example.deokbook.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.book.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
}
