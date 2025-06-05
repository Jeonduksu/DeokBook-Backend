package org.example.deokbook.domain.loan.repository;

import org.example.deokbook.domain.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
