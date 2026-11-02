package br.edu.ifsp.carregaja.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifsp.carregaja.model.Vaga;
import br.edu.ifsp.carregaja.service.ApiException;
import br.edu.ifsp.carregaja.service.VagaRestClient;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class ManterVagasBean extends BeanBase {

  private static final long serialVersionUID = 1L;

  @Inject
  private VagaRestClient vagaRestClient;
  @Getter
  private List<Vaga> vagas = new ArrayList<>();
  @Getter
  @Setter
  private Vaga vaga = new Vaga();

  public void inicializar() {
    if (!FacesContext.getCurrentInstance().isPostback()) {
      carregar();
    }
  }

  public void editar(Vaga selecionada) {
    vaga = new Vaga();
    vaga.setId(selecionada.getId());
    vaga.setIdentificacao(selecionada.getIdentificacao());
    vaga.setPotenciaCarregador(selecionada.getPotenciaCarregador());
  }

  public void limpar() {
    vaga = new Vaga();
  }

  public void salvar() {
    try {
      if (vaga.getId() == null) {
        vagaRestClient.criar(vaga);
        informar("Vaga cadastrada com sucesso.");
      } else {
        vagaRestClient.atualizar(vaga);
        informar("Vaga alterada com sucesso.");
      }
      limpar();
      carregar();
    } catch (ApiException e) {
      reclamar(e);
    }
  }

  public void desativar(Vaga selecionada) {
    executar(() -> vagaRestClient.desativar(selecionada.getId()), "Vaga desativada: não aceita novas sessões.");
  }

  public void reativar(Vaga selecionada) {
    executar(() -> vagaRestClient.reativar(selecionada.getId()), "Vaga reativada.");
  }

  public void remover(Vaga selecionada) {
    executar(() -> vagaRestClient.remover(selecionada.getId()), "Vaga removida.");
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
      vagas = vagaRestClient.listarTodas();
    } catch (ApiException e) {
      reclamar(e);
    }
  }

  @FunctionalInterface
  private interface Acao {
    void executar() throws ApiException;
  }
}
