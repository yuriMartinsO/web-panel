package br.com.webpanel.deploy.images.dto;

import java.time.Instant;

public record RecoveryImageDto(
    Long id,
    String name,
    String base64,
    Long size,
    Instant createdAt,
    Instant updatedAt
) {}