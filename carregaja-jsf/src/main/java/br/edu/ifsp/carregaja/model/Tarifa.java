package br.edu.ifsp.carregaja.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tarifa implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private BigDecimal valorKwh;
  private LocalDateTime inicioVigencia;
  private String nomeResponsavel;
}
