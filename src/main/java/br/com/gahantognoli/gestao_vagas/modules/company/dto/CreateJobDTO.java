package br.com.gahantognoli.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {

  @Schema(example = "Vaga para pessoa desenvolvedora JR", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;

  @Schema(example = "Vale alimentação, plano de saúde...", requiredMode = Schema.RequiredMode.REQUIRED)
  private String benefits;

  @Schema(example = "JR", requiredMode = Schema.RequiredMode.REQUIRED)
  private String level;
}
