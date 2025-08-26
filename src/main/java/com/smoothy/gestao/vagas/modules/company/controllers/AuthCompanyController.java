package com.smoothy.gestao.vagas.modules.company.controllers;

import com.smoothy.gestao.vagas.modules.company.dto.AuthCompanyDTO;
import com.smoothy.gestao.vagas.modules.company.services.AuthCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Endpoints relacionados as company")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyService authCompanyService;

    @PostMapping("/auth")
    @Operation(summary = "Login de company's", description = "Essa função é responsável efetuar o login de uma empresa")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            var result = this.authCompanyService.execute(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
