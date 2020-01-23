package prova.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulacaoLojistaWrapper {
  private SimulacaoLojista[] simulacoes;

  /** Calcula os valores das simulacoes */
  public void calcularSimulacoes() {
    for(SimulacaoLojista s : simulacoes) {
      s.cacularValores();
    }
  }

  public SimulacaoLojista[] getSimulacoes() {
    return simulacoes;
  }

  public void setSimulacoes(SimulacaoLojista[] simulacoes) {
    this.simulacoes = simulacoes;
  }
}