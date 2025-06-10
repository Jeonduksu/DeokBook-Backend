package org.example.deokbook.domain.loan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.deokbook.domain.book.entity.Book;
import org.example.deokbook.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_date")
    private LocalDateTime loanDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public void updateLoan(LocalDateTime loanDate, LocalDateTime dueDate, LocalDateTime returnDate, Book book, User user) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.book = book;
        this.user = user;
    }

    public void returnLoan(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
