package org.example.deokbook.domain.loan.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.book.entity.Book;
import org.example.deokbook.domain.book.repository.BookRepository;
import org.example.deokbook.domain.book.service.BookService;
import org.example.deokbook.domain.loan.entity.Loan;
import org.example.deokbook.domain.loan.repository.LoanRepository;
import org.example.deokbook.domain.user.entity.User;
import org.example.deokbook.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    
    //대출 현황
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }
    
    //대출
    public Loan loanBook(Long id, Long bookId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당된 유저를 찾을 수 없습니다."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("해당된 책을 찾을 수 없습니다."));

        if (!book.isAvailable()) {
            throw new IllegalArgumentException("해당 책은 대출이 불가능합니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueDate = now.plusDays(14);


        Loan loan = new Loan();
        loan.updateLoan(now,dueDate,null,book,user);

        book.setAvailable('N');
        bookRepository.save(book);

        return  loanRepository.save(loan);
    }
    
    //반납
    public Loan returnBook(Long userId, Long bookId) {
        Loan loan = loanRepository.findByUserIdAndBookIdAndReturnDateIsNull(userId,bookId)
                .orElseThrow(() -> new IllegalArgumentException("대출 기록이 없습니다."));

        loan.returnLoan(LocalDateTime.now());

        Book book = loan.getBook();
        book.setAvailable('Y');
        bookService.saveBook(book);
        
        return loanRepository.save(loan);
    }
}
