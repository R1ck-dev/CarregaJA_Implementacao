package br.edu.ifsp.carregaja.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;

import br.edu.ifsp.carregaja.model.Tarifa;
import br.edu.ifsp.carregaja.model.TarifaRequest;

@ApplicationScoped
public class TarifaRestClient implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String CAMINHO = "/tarifas";

  @Inject
  private ApiClient api;

  public List<Tarifa> listarTodas() throws ApiException {
    return api.get(CAMINHO, new TypeReference<List<Tarifa>>() {
    }, "carregar as tarifas");
  }

  public void criar(BigDecimal valorKwh) throws ApiException {
    api.enviarSemResposta("POST", CAMINHO, new TarifaRequest(valorKwh), "cadastrar a tarifa");
  }
}
