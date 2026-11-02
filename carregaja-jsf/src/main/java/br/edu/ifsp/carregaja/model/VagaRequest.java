package br.edu.ifsp.carregaja.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VagaRequest {
  private final String identificacao;
  private final Double potenciaCarregador;

  public static VagaRequest de(Vaga vaga) {
    return new VagaRequest(vaga.getIdentificacao(), vaga.getPotenciaCarregador());
  }
}
