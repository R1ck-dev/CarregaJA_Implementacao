package br.edu.ifsp.carregaja.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessaoRequest {
  private final Long vagaId;
  private final Integer nivelBateria;
}
