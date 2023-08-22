package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @Email
    @NotEmpty(message = "email should not be null or empty")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "body should be at least 10 characters")
    private String body;
}
