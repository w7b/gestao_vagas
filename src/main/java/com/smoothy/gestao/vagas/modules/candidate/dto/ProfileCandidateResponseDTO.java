package com.smoothy.gestao.vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileCandidateResponseDTO {
    @Schema(example = "Desenvolvedor java")
    private String description;

    @Schema(example = "gabriel")
    private String username;

    @Schema(example = "Gabriel Chedid")
    private String name;

    @Schema(example = "gabriel@email.com")
    private String email;

    private UUID id;
}
