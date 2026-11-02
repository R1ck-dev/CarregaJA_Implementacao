package br.edu.ifsp.carregaja.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.ifsp.carregaja.model.Tarifa;
import br.edu.ifsp.carregaja.service.ApiException;
import br.edu.ifsp.carregaja.service.TarifaRestClient;

import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class DefinirTarifaBean extends BeanBase {

  private static final long serialVersionUID = 1L;

  @Inject
  private TarifaRestClient tarifaRestClient;
  @Getter
  private List<Tarifa> tarifas = new ArrayList<>();
  @Getter
  @Setter
  private BigDecimal valorKwh;

  public void inicializar() {
    if (!FacesContext.getCurrentInstance().isPostback()) {
      carregar();
    }
  }

  public void salvar() {
    try {
      tarifaRestClient.criar(valorKwh);
      informar("Tarifa cadastrada: vale para as sessões iniciadas a partir de agora.");
      valorKwh = null;
      carregar();
    } catch (ApiException e) {
      reclamar(e);
    }
  }

  private void carregar() {
    try {
      tarifas = tarifaRestClient.listarTodas();
    } catch (ApiException e) {
      reclamar(e);
    }
  }
}
