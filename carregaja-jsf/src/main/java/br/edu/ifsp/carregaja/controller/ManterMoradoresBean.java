package br.edu.ifsp.carregaja.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifsp.carregaja.model.Usuario;
import br.edu.ifsp.carregaja.model.UsuarioRequest;
import br.edu.ifsp.carregaja.service.ApiException;
import br.edu.ifsp.carregaja.service.UsuarioRestClient;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ManterMoradoresBean extends BeanBase {

  private static final long serialVersionUID = 1L;

  @Inject
  private UsuarioRestClient usuarioRestClient;
  @Getter
  private List<Usuario> usuarios = new ArrayList<>();
  @Getter
  private Long idEmEdicao;
  @Getter
  @Setter
  private String nome;
  @Getter
  @Setter
  private String email;
  @Getter
  @Setter
  private String apartamento;
  @Getter
  @Setter
  private boolean sindico;
  @Getter
  @Setter
  private String senhaProvisoria;

  public void inicializar() {
    if (!FacesContext.getCurrentInstance().isPostback()) {
      carregar();
    }
  }

  public boolean isEditando() {
    return idEmEdicao != null;
  }

  public void editar(Usuario usuario) {
    idEmEdicao = usuario.getId();
    nome = usuario.getNome();
    email = usuario.getEmail();
    apartamento = usuario.getApartamento() == null ? null : usuario.getApartamento().getNumero();
    sindico = usuario.getPerfis().contains("SINDICO");
    senhaProvisoria = null;
  }

  public void salvar() {
    String senha = senhaProvisoria == null || senhaProvisoria.isEmpty() ? null : senhaProvisoria;
    UsuarioRequest requisicao = new UsuarioRequest(nome, email, apartamento, sindico, senha);
    try {
      if (isEditando()) {
        usuarioRestClient.atualizar(idEmEdicao, requisicao);
        informar("Morador alterado com sucesso.");
      } else {
        usuarioRestClient.criar(requisicao);
        informar("Morador cadastrado. Informe a senha provisória a ele.");
      }
      limpar();
      carregar();
    } catch (ApiException e) {
      reclamar(e);
    }
  }

  public void limpar() {
    idEmEdicao = null;
    nome = null;
    email = null;
    apartamento = null;
    sindico = false;
    senhaProvisoria = null;
  }

  public void desativar(Usuario usuario) {
    executar(() -> usuarioRestClient.desativar(usuario.getId()), "Morador desativado: o acesso foi revogado.");
  }

  public void reativar(Usuario usuario) {
    executar(() -> usuarioRestClient.reativar(usuario.getId()), "Morador reativado.");
  }

  public void remover(Usuario usuario) {
    executar(() -> usuarioRestClient.remover(usuario.getId()), "Morador removido.");
  }

  private void executar(Acao acao, String mensagemDeSucesso) {
    try {
      acao.executar();
      informar(mensagemDeSucesso);
      carregar();
    } catch (ApiException e) {
      reclamar(e);
    }
  }

  private void carregar() {
    try {
      usuarios = usuarioRestClient.listarTodos();
    } catch (ApiException e) {
      reclamar(e);
    }
  }

  @FunctionalInterface
  private interface Acao {
    void executar() throws ApiException;
  }
}
