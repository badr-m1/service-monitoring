package com.example.api.DTO;

import jakarta.validation.constraints.NotBlank;

public record ServiceRequestDTO(@NotBlank String name, @NotBlank String url) {}