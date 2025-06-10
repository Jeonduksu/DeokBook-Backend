package org.example.deokbook.domain.loan.repository;

import org.example.deokbook.domain.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByUserIdAndBookIdAndReturnDateIsNull(Long userId, Long bookId);
}
