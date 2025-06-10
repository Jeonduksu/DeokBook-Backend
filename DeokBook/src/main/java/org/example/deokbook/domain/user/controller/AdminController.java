package org.example.deokbook.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.domain.book.entity.Book;
import org.example.deokbook.domain.book.service.BookService;
import org.example.deokbook.domain.loan.entity.Loan;
import org.example.deokbook.domain.loan.service.LoanService;
import org.example.deokbook.domain.user.entity.User;
import org.example.deokbook.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BookService bookService;
    private final LoanService loanService;
    
    //사용자 목록
    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    //사용자 등록
    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
    
    // 사용자 수정
    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id,user);
    }
    
    //사용자 삭제
    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @PutMapping("/book/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id,book);
    }

    @GetMapping("/loans")
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    @PostMapping("/loans")
    public Loan loanBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return loanService.loanBook(userId,bookId);
    }

    @PostMapping("/loans/returns")
    public Loan returnBook(@RequestParam Long userId, @RequestParam Long bookId) {
        return loanService.returnBook(userId,bookId);
    }
}
