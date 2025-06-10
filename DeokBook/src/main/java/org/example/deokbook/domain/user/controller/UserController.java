package org.example.deokbook.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.book.entity.Book;
import org.example.deokbook.domain.book.service.BookService;
import org.example.deokbook.domain.loan.entity.Loan;
import org.example.deokbook.domain.loan.service.LoanService;
import org.example.deokbook.domain.user.entity.User;
import org.example.deokbook.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BookService bookService;
    private final LoanService loanService;

    // 책 검색
    @GetMapping("/book")
    public List<Book> searchBook(   @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String publisher,
                                    @RequestParam(required = false) String title,
                                    @RequestParam(required = false) String author) {
        return bookService.search(keyword,publisher, title, author);
    }

    // 책 대출 가능 여부
    @GetMapping("/book/{id}")
    public boolean checkBook(@PathVariable Long id) {
        return bookService.isAvailable(id);
    }

    // 대출 현황
    @PostMapping("/loans")
    public Loan requestLoan(@RequestParam Long BookId, @AuthenticationPrincipal User user) {
        return loanService.loanBook(user.getId(), BookId);
    }

    //반납
    @PostMapping("/returns")
    public Loan returnBook(@RequestParam Long BookId, @AuthenticationPrincipal User user) {
        return loanService.returnBook(user.getId(), BookId);
    }


    @GetMapping("/loans/overdue")
    public List<Loan> overdueLoans(@AuthenticationPrincipal User user) {
        return loanService.getOverdueLoans(user.getId());
    }
}
