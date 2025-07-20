package br.com.gahantognoli.gestao_vagas.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gahantognoli.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.gahantognoli.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.gahantognoli.gestao_vagas.modules.exceptions.UserFoundException;

@Service
public class CreateCandidateUseCase {
  
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity){
    this.candidateRepository
        .findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
        .ifPresent((user) -> {
          throw new UserFoundException();
        });

    return this.candidateRepository.save(candidateEntity);
  }

}
