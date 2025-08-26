package com.smoothy.gestao.vagas.modules.company.controllers;


import com.smoothy.gestao.vagas.modules.company.dto.CreateJobDTO;
import com.smoothy.gestao.vagas.modules.company.entity.CompanyEntity;
import com.smoothy.gestao.vagas.modules.company.repositories.CompanyRepository;
import com.smoothy.gestao.vagas.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static com.smoothy.gestao.vagas.utils.TestUtils.objectToJson;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private static final String SECRET = "jorge-come-casadas-dentro-e-fora-de-casa-pulando-o-muro";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
    }

    @Test
    @DisplayName("Should be able to create a new job")
    public void should_be_able_to_create_a_new_job() throws Exception {

        var company = CompanyEntity.builder()
        .description("COMPANY_DESCRIPTION")
        .email("company@email")
        .password("1234567890")
        .username("COMPANY_USERNAME")
        .website("http://company.com")
        .name("COMPANY_NAME").build();

        company = companyRepository.saveAndFlush(company);

        var createJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", TestUtils.generateToken(company.getId(), SECRET))
        .content(objectToJson(createJobDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println(result);
    }


    @Test
    public void should_not_be_able_to_create_a_new_job_if_company_not_found() throws Exception {
        var createJobDTO = CreateJobDTO.builder()
        .benefits("BENEFITS_TEST")
        .description("DESCRIPTION_TEST")
        .level("LEVEL_TEST")
        .build();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), SECRET))
        .content(objectToJson(createJobDTO)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
