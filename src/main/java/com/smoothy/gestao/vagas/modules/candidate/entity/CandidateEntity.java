package com.smoothy.gestao.vagas.modules.candidate.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Daniel de Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank()
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaços.")
    @Schema(example = "daniel", pattern = "\\S+", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Email(message = "O campo (email) deve conter um email valido")
    @Schema(example = "daniel@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Length(min = 10, max = 100, message = "A [senha] deve conter entre 10 e 100 caracteres")
    @Schema(example = "daniel1234", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(example = "Desenvolvedor java junior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDate createdAt;
}
