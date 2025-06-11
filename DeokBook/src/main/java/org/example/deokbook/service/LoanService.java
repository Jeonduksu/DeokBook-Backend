package org.example.deokbook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.deokbook.dto.LoanDto;
import org.example.deokbook.dto.LoanRequest;
import org.example.deokbook.entity.Book;
import org.example.deokbook.Repository.BookRepository;
import org.example.deokbook.entity.Loan;
import org.example.deokbook.Repository.LoanRepository;
import org.example.deokbook.entity.User;
import org.example.deokbook.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    
    //대출 현황
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Transactional
    public LoanDto loanBook(LoanRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        if (!book.isAvailable()) {
            throw new RuntimeException("책을 대출 할수 없습니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusDays(15);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(now);
        loan.setDueDate(dueDate);
        loan.setReturned(false);

        // 책을 대출 불가능 상태로 변경
        book.setAvailable(false);
        bookRepository.save(book);

        Loan savedLoan = loanRepository.save(loan);
        return convertToDTO(savedLoan);
    }

    @Transactional
    public LoanDto returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("대출을 찾을 수 없습니다."));

        if (loan.isReturned()) {
            throw new RuntimeException("이미 대출 중입니다.");
        }

        loan.setReturnDate(LocalDateTime.now());
        loan.setReturned(true);

        // 책을 대출 가능 상태로 변경
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        Loan savedLoan = loanRepository.save(loan);
        return convertToDTO(savedLoan);
    }

    public List<LoanDto> getAllActiveLoans() {
        return loanRepository.findByReturnedFalse().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LoanDto> getOverdueLoans() {
        return loanRepository.findOverdueLoans().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LoanDto convertToDTO(Loan loan) {
        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        dto.setUserId(loan.getUser().getId());
        dto.setUserName(loan.getUser().getName());
        dto.setBookId(loan.getBook().getId());
        dto.setBookTitle(loan.getBook().getTitle());
        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setReturned(loan.isReturned());
        dto.setOverdue(!loan.isReturned() && loan.getDueDate().isBefore(LocalDateTime.now()));
        return dto;
    }

}
