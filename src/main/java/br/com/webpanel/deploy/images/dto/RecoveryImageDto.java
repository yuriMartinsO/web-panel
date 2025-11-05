package br.com.webpanel.deploy.images.dto;

public record RecoveryImageDto(
    Long id,
    String name,
    String base64,
    Long size
) {}