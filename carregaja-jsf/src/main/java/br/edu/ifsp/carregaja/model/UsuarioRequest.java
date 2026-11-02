package br.edu.ifsp.carregaja.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioRequest {
  private final String nome;
  private final String email;
  private final String apartamento;
  private final boolean sindico;
  private final String senhaProvisoria;
}
