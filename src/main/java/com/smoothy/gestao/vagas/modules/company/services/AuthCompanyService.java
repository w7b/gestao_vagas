package com.smoothy.gestao.vagas.modules.company.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.smoothy.gestao.vagas.modules.company.dto.AuthCompanyDTO;
import com.smoothy.gestao.vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.smoothy.gestao.vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyService {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var companyExists = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username/Password incorrect"));

        //Verifica a senha são igual
        var passwordMatchs = this.passwordEncoder.matches(authCompanyDTO.getPassword(), companyExists.getPassword());

        // Se não for igual -> Erro
        if(!passwordMatchs) {
            throw new AuthenticationException("");
        }

        // Se for igual -> gerar token

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withSubject(companyExists.getId().toString())
                .sign(algorithm);

        var response = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli()) // -> Converte pra mili segundos
                .build();

        return response;
    }
}
