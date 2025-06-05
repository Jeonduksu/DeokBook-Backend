package org.example.deokbook.domain.loan.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.loan.repository.LoanRepository;
import org.example.deokbook.domain.loan.service.LoanService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
}
