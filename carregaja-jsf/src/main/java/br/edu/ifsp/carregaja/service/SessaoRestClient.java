package br.edu.ifsp.carregaja.service;

import java.io.Serializable;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;

import br.edu.ifsp.carregaja.model.Sessao;
import br.edu.ifsp.carregaja.model.SessaoRequest;

@ApplicationScoped
public class SessaoRestClient implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String CAMINHO = "/sessoes";

  @Inject
  private ApiClient api;

  public Optional<Sessao> buscarMinha() throws ApiException {
    return api.getOpcional(CAMINHO + "/minha", new TypeReference<Sessao>() {
    }, "carregar a sessão");
  }

  public Sessao iniciar(SessaoRequest requisicao) throws ApiException {
    return api.post(CAMINHO, requisicao, new TypeReference<Sessao>() {
    }, "iniciar a recarga");
  }
}
