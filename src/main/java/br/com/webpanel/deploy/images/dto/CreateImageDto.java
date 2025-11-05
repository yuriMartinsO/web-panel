package br.com.webpanel.deploy.images.dto;

public record CreateImageDto(
    String name,
    String base64,
    Long size
) {}