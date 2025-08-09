package br.com.gahantognoli.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.gahantognoli.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.gahantognoli.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.gahantognoli.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.gahantognoli.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "security.token.secret=JAVAGAS_1234@#")
@ActiveProfiles("test")
public class CreateJobControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private CompanyRepository companyRepository;

  @Value("${security.token.secret}")
  private String secret;

  @Before
  public void setup() {
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void should_be_able_to_create_a_new_job() throws Exception {
    var company = CompanyEntity.builder()
        .name("Tech Solutions")
        .description("Innovative tech company specializing in software solutions.")
        .email("contact@techsolutions.com")
        .username("techsolutions")
        .password("securepassword@1234")
        .build();
    company = companyRepository.saveAndFlush(company);

    CreateJobDTO createJobDTO = CreateJobDTO.builder()
        .benefits("Health insurance, remote work")
        .description("Develop and maintain software applications.")
        .level("JR")
        .build();

    mvc.perform(
        MockMvcRequestBuilders.post("/company/job")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.objectToJson(createJobDTO))
            .header("Authorization", TestUtils.generateToken(company.getId(), secret)))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void should_not_be_able_to_create_a_new_job_with_company_not_found() throws Exception {
    CreateJobDTO createJobDTO = CreateJobDTO.builder()
        .benefits("Health insurance, remote work")
        .description("Develop and maintain software applications.")
        .level("JR")
        .build();

    mvc.perform(
        MockMvcRequestBuilders.post("/company/job")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtils.objectToJson(createJobDTO))
            .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), secret)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

}
