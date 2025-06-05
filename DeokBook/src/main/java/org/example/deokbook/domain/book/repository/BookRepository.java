package org.example.deokbook.domain.book.repository;

import org.example.deokbook.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
