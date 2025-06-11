package org.example.deokbook.Repository;

import org.example.deokbook.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByReturnedFalse();

    List<Loan> findByUserIdAndReturnedFalse(Long userId);

    List<Loan> findByBookIdAndReturnedFalse(Long bookId);

    @Query("SELECT l FROM Loan l WHERE l.book.id = :bookId AND l.returned = false")
    Optional<Loan> findActiveByBookId(@Param("bookId") long bookId);

    @Query("SELECT l FROM Loan l WHERE l.dueDate < CURRENT_TIMESTAMP AND l.returned = false")
    List<Loan> findOverdueLoans();
}
