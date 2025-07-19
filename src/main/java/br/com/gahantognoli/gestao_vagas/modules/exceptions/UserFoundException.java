package br.com.gahantognoli.gestao_vagas.modules.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException(String message){
    super("Usuário já existe");
  }
}
