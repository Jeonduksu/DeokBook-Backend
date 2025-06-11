package org.example.deokbook.service;

import lombok.RequiredArgsConstructor;
import org.example.deokbook.Repository.BookRepository;
import org.example.deokbook.Repository.LoanRepository;
import org.example.deokbook.dto.BookDto;
import org.example.deokbook.dto.LoanDto;
import org.example.deokbook.dto.UserDto;
import org.example.deokbook.entity.Book;
import org.example.deokbook.entity.Loan;
import org.example.deokbook.entity.User;
import org.example.deokbook.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    public UserDto createUser(UserDto userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setMemo(userDTO.getMemo());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setMemo(userDTO.getMemo());

        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setMemo(user.getMemo());

        // 현재 대출 중인 도서들
        List<LoanDto> activeLoans = loanRepository.findByUserIdAndReturnedFalse(user.getId())
                .stream()
                .map(loan -> {
                    LoanDto loanDto = new LoanDto();
                    loanDto.setId(loan.getId());
                    loanDto.setBookId(loan.getBook().getId());
                    loanDto.setBookTitle(loan.getBook().getTitle());
                    loanDto.setLoanDate(loan.getLoanDate());
                    loanDto.setDueDate(loan.getDueDate());
                    loanDto.setOverdue(loan.getDueDate().isBefore(LocalDateTime.now()));
                    return loanDto;
                })
                .collect(Collectors.toList());

        dto.setActiveLoans(activeLoans);
        return dto;
    }
}
