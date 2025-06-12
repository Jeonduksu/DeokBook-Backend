package org.example.deokbook.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.dto.LoanDto;
import org.example.deokbook.dto.LoanRequest;
import org.example.deokbook.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanDto> loanBook(@RequestBody LoanRequest request) {
        try {
            LoanDto loan = loanService.loanBook(request);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanDto> returnBook(@PathVariable Long id) {
        try {
            LoanDto loan = loanService.returnBook(id);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<LoanDto>> getActiveLoans() {
        List<LoanDto> loans = loanService.getAllActiveLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<LoanDto>> getOverdueLoans() {
        List<LoanDto> loans = loanService.getOverdueLoans();
        return ResponseEntity.ok(loans);
    }
}
