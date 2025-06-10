package org.example.deokbook.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.deokbook.domain.loan.entity.Loan;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String email;
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    private String userMemo;

    @NotBlank
    private Character rule;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();
    
    //업데이트용 메서드
    public User updateUser(String email, String password, String name, String phone, String userMemo, Character rule) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.userMemo = userMemo;
        this.rule = rule;
    }
}
