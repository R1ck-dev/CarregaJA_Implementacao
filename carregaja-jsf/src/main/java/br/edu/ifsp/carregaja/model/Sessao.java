package br.edu.ifsp.carregaja.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sessao implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long id;
  private String vaga;
  private String veiculo;
  private Integer nivelBateriaInicial;
  private Double potenciaEfetiva;
  private Double energiaARepor;
  private BigDecimal valorKwh;
  private LocalDateTime inicio;
  private LocalDateTime previsaoConclusao;
  private String estado;

  public String getTempoEstimado() {
    Duration tempo = Duration.between(inicio, previsaoConclusao);
    return String.format("%dh%02d", tempo.toHours(), tempo.toMinutesPart());
  }

  public String getDescricaoEstado() {
    switch (estado) {
      case "EXCEDIDA":
        return "Excedida: passou da duração máxima. Encerre assim que retirar o veículo.";
      case "CONCLUIDA_AINDA_OCUPADA":
        return "Concluída pela previsão, e a vaga ainda está ocupada.";
      default:
        return "Em andamento.";
    }
  }
}
