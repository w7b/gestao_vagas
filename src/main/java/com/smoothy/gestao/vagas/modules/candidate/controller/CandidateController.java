package com.smoothy.gestao.vagas.modules.candidate.controller;


import com.smoothy.gestao.vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.smoothy.gestao.vagas.modules.candidate.entity.CandidateEntity;
import com.smoothy.gestao.vagas.modules.candidate.service.ApplyJobCandidateService;
import com.smoothy.gestao.vagas.modules.candidate.service.CandidateService;
import com.smoothy.gestao.vagas.modules.candidate.service.ListAllJobsService;
import com.smoothy.gestao.vagas.modules.candidate.service.ProfileCandidateService;
import com.smoothy.gestao.vagas.modules.company.entity.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Endpoints relacionados ao candidato")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    @Autowired
    private ProfileCandidateService profileCandidateService;
    @Autowired
    private ListAllJobsService listAllJobsService;
    @Autowired
    private ApplyJobCandidateService applyJobCandidateService;

    @PostMapping("/")
    @Operation(summary = "Cadastro de candidatos", description = "endpoint responsável por cadastrar candidatos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "User has exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
        try {
            var result = this.candidateService.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {

        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateService.execute(
                    UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponiveis ao candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return this.listAllJobsService.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Listagem de vagas disponiveis ao candidato", description = "Essa funçao é responsável por realizar a inscriçao do candidato em uma vaga")
    public ResponseEntity<Object> applyJobEntity(ServletRequest request, @RequestBody UUID idJob) {
        try {
            var idCandidate = request.getAttribute("candidate_id");
            var result = this.applyJobCandidateService.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
