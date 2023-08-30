package org.example.dto;

import java.time.LocalDate;

public record PersonCreateRequest(String name, String email, String password, LocalDate dob) {
}
