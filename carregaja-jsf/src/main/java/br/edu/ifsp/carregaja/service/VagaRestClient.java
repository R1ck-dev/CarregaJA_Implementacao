package br.edu.ifsp.carregaja.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;

import br.edu.ifsp.carregaja.model.Vaga;
import br.edu.ifsp.carregaja.model.VagaRequest;

@ApplicationScoped
public class VagaRestClient implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String CAMINHO = "/vagas";

  @Inject
  private ApiClient api;

  public List<Vaga> listarTodas() throws ApiException {
    return api.get(CAMINHO, new TypeReference<List<Vaga>>() {
    }, "carregar as vagas");
  }

  public void criar(Vaga vaga) throws ApiException {
    api.enviarSemResposta("POST", CAMINHO, VagaRequest.de(vaga), "cadastrar a vaga");
  }

  public void atualizar(Vaga vaga) throws ApiException {
    api.enviarSemResposta("PUT", CAMINHO + "/" + vaga.getId(), VagaRequest.de(vaga), "alterar a vaga");
  }

  public void desativar(Long id) throws ApiException {
    api.enviarSemResposta("POST", CAMINHO + "/" + id + "/desativar", null, "desativar a vaga");
  }

  public void reativar(Long id) throws ApiException {
    api.enviarSemResposta("POST", CAMINHO + "/" + id + "/reativar", null, "reativar a vaga");
  }

  public void remover(Long id) throws ApiException {
    api.enviarSemResposta("DELETE", CAMINHO + "/" + id, null, "remover a vaga");
  }
}
