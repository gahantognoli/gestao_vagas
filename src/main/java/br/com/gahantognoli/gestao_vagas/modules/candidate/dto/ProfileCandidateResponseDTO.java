package br.com.gahantognoli.gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

  @Schema(example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID id;

  @Schema(example = "João da Silva")
  private String name;

  @Schema(example = "joao.silva")
  private String username;

  @Schema(example = "joao.silva@example.com")
  private String email;

  @Schema(example = "Desenvolvedor Java")
  private String description;
}
