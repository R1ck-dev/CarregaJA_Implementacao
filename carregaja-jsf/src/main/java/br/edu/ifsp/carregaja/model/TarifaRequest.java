package br.edu.ifsp.carregaja.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TarifaRequest {
  private final BigDecimal valorKwh;
}
