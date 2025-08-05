package br.com.gahantognoli.gestao_vagas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PrimeiroTeste {

  @Test
  public void deve_ser_possivel_calcular_dois_numeros(){
    var resultado = calculate(2, 2);
    assertEquals(4, resultado);
  }

  @Test
  public void validar_valor_incorreto(){
    var resultado = calculate(2, 2);
    assertNotEquals(5, resultado);
  }

  public static int calculate(int num1, int num2){
    return num1 + num2;
  }
}
