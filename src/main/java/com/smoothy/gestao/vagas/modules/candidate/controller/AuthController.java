package com.smoothy.gestao.vagas.modules.candidate.controller;

import com.smoothy.gestao.vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import com.smoothy.gestao.vagas.modules.candidate.service.AuthCandidateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Endpoints relacionados ao candidato")
public class AuthController {


    @Autowired
    private AuthCandidateService authCandidateService;

    @PostMapping("/auth")
    @Operation(summary = "Login para o candidato", description = "Essa função é responsável para logar um candidato")

    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var token = this.authCandidateService.execute(authCandidateRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
