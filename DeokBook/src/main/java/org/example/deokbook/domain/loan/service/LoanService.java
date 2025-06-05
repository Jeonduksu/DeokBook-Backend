package org.example.deokbook.domain.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
}
