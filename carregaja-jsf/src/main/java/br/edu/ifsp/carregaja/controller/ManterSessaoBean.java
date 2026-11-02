package br.edu.ifsp.carregaja.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifsp.carregaja.model.Sessao;
import br.edu.ifsp.carregaja.model.SessaoRequest;
import br.edu.ifsp.carregaja.model.Vaga;
import br.edu.ifsp.carregaja.model.Veiculo;
import br.edu.ifsp.carregaja.service.ApiException;
import br.edu.ifsp.carregaja.service.SessaoRestClient;
import br.edu.ifsp.carregaja.service.VagaRestClient;
import br.edu.ifsp.carregaja.service.VeiculoRestClient;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ManterSessaoBean extends BeanBase {

  private static final long serialVersionUID = 1L;

  @Inject
  private SessaoRestClient sessaoRestClient;
  @Inject
  private VagaRestClient vagaRestClient;
  @Inject
  private VeiculoRestClient veiculoRestClient;
  @Getter
  private Veiculo veiculo;
  @Getter
  private Sessao sessao;
  @Getter
  private List<Vaga> vagas = new ArrayList<>();
  @Getter
  @Setter
  private Long vagaId;
  @Getter
  @Setter
  private Integer nivelBateria;

  public void inicializar() {
    if (FacesContext.getCurrentInstance().isPostback()) {
      return;
    }
    try {
      veiculo = veiculoRestClient.buscarMeu().orElse(null);
      sessao = sessaoRestClient.buscarMinha().orElse(null);
      // Todas as ativas, e não só as livres: tentar uma ocupada é a demanda que o UC12 conta (RF-06)
      vagas = vagaRestClient.listarTodas().stream().filter(Vaga::isAtiva).collect(Collectors.toList());
    } catch (ApiException e) {
      reclamar(e);
    }
  }

  public void iniciar() {
    try {
      sessao = sessaoRestClient.iniciar(new SessaoRequest(vagaId, nivelBateria));
      informar("Recarga iniciada na vaga " + sessao.getVaga() + ".");
    } catch (ApiException e) {
      reclamar(e);
    }
  }
}
