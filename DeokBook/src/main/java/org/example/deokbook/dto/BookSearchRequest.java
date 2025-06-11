package org.example.deokbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchRequest {
    private String title;
    private String author;
    private String publisher;
    private String sortBy = "title";
    private String sortDirection = "asc";
    private Integer page = 0;
    private Integer pageSize = 10;
}
