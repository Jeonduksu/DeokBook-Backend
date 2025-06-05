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
}
