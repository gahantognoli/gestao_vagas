package br.com.gahantognoli.gestao_vagas.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobDTO {
  private String description;
  private String benefits;
  private String level;
}
