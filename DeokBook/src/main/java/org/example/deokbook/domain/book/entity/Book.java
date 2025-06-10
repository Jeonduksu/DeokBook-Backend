package org.example.deokbook.domain.book.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.deokbook.domain.loan.entity.Loan;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String publisher;

    @Column(name = "published_date")
    private String publishedDate;

    @NotNull
    private Integer price;

    @NotBlank
    private Character status;

    @OneToMany(mappedBy = "book")
    private List<Loan> loans = new ArrayList<>();


    public void updateBook(String title, String author, String publisher, String publishedDate, Integer price, Character status) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.price = price;
        this.status = status;
    }

    public boolean isAvailable() {
        return this.status != null && this.status == 'Y';
    }

    public void setAvailable(Character status) {
        this.status = status;
    }
}
