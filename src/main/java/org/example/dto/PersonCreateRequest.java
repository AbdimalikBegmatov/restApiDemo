package org.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PersonCreateRequest {
    @NotNull(message = "cant be null")
    private String name;
    @NotNull(message = "cant be null")
    @Email
    private String email;
    @NotNull(message = "cant be null")
    private String password;
    @NotNull(message = "cant be null")
    private LocalDate dob;
}
