package br.com.gahantognoli.gestao_vagas.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gahantognoli.gestao_vagas.exceptions.UserFoundException;
import br.com.gahantognoli.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.gahantognoli.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.gahantognoli.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.gahantognoli.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.gahantognoli.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.gahantognoli.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.gahantognoli.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

  @Autowired
  private ApplyJobCandidateUseCase applyJobCandidateUseCase;

  @PostMapping
  @Operation(summary = "Cadastro de candidato", description = "Essa função é responsável por cadastrar um novo candidato.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CandidateEntity.class))), description = "Perfil do candidato retornado com sucesso"),
      @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(type = "string", example = "Usuário já existe.")), description = "Usuário já existe.")
  })
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = createCandidateUseCase.execute(candidateEntity);
      return ResponseEntity.ok().body(result);
    } catch (UserFoundException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))), description = "Perfil do candidato retornado com sucesso"),
      @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(type = "string", example = "User not found")), description = "User not found")
  })
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> getProfile(HttpServletRequest request) {
    try {
      var idCandidate = request.getAttribute("candidate_id");
      var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.ok().body(profile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Listar vagas disponíveis para o candidato", description = "Essa função é responsável por listar todas as vagas disponíveis baseada no filtro.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))), description = "Lista de vagas retornada com sucesso")
  })
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> getJobs(@RequestParam String filter) {
    return this.listAllJobsByFilterUseCase.execute(filter);
  }

  @PostMapping("/job/apply")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Operation(summary = "Candidatar-se a uma vaga", description = "Essa função é responsável por cadastrar a candidatura do candidato a uma vaga.")
  @SecurityRequirement(name = "jwt_auth")
  public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
    try {
      var idCandidate = request.getAttribute("candidate_id");
      var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
