package br.edu.ifsp.carregaja.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;

import br.edu.ifsp.carregaja.model.Usuario;
import br.edu.ifsp.carregaja.model.UsuarioRequest;

@ApplicationScoped
public class UsuarioRestClient implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String CAMINHO = "/usuarios";

  @Inject
  private ApiClient api;

  public List<Usuario> listarTodos() throws ApiException {
    return api.get(CAMINHO, new TypeReference<List<Usuario>>() {
    }, "carregar os moradores");
  }

  public void criar(UsuarioRequest requisicao) throws ApiException {
    api.enviarSemResposta("POST", CAMINHO, requisicao, "cadastrar o morador");
  }

  public void atualizar(Long id, UsuarioRequest requisicao) throws ApiException {
    api.enviarSemResposta("PUT", CAMINHO + "/" + id, requisicao, "alterar o morador");
  }

  public void desativar(Long id) throws ApiException {
    api.enviarSemResposta("POST", CAMINHO + "/" + id + "/desativar", null, "desativar o morador");
  }

  public void reativar(Long id) throws ApiException {
    api.enviarSemResposta("POST", CAMINHO + "/" + id + "/reativar", null, "reativar o morador");
  }

  public void remover(Long id) throws ApiException {
    api.enviarSemResposta("DELETE", CAMINHO + "/" + id, null, "remover o morador");
  }
}
