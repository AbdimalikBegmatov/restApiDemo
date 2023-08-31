package org.example.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PersonResponseDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private LocalDate dob;
    private LocalDateTime createAt;
    private int age;
}
