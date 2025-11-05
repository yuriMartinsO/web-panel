package br.com.webpanel.deploy.categories.dto;

import java.time.Instant;

/**
 * DTO returned to clients with audit timestamps.
 */
public record RecoveryCategoryDto(
    Long id,
    String name,
    Instant createdAt,
    Instant updatedAt
) {}
