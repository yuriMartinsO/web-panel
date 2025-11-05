package br.com.webpanel.deploy.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new Category.
 */
public record CreateCategoryDto(
    @NotBlank(message = "name is required")
    @Size(max = 255, message = "name must not exceed 255 characters")
    String name
) {}
