package org.example.deokbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private Integer publishYear;
    private Integer price;
    private boolean available;
    private boolean overdue;
}
