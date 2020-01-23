package prova.modelo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulacaoLojista {
  private Produto[] arrProdutos;

  private int quantidade;
  private double financeiro;
  private double precoMedio;
  
  /** Soma o total dos produtos de uma simulacao */
  public Integer somarProdutos() {
    quantidade = 0;
    for(Produto p : arrProdutos) {
      quantidade += p.getQuantity();
    }
    return quantidade;
  }

  /**Calcula os valores da simulacao*/
  public void cacularValores() {
    somarProdutos();

    financeiro = 0;
    for(Produto p : arrProdutos) {
      try {
        NumberFormat nf = DecimalFormat.getCurrencyInstance(Locale.US);
        financeiro += p.getQuantity() * nf.parse(p.getPrice()).doubleValue();
      } catch (ParseException e) {
        //pula o registro
      }
    }

    precoMedio = financeiro / quantidade;
  }

  public Produto[] getArrProdutos() {
    return arrProdutos;
  }

  public void setArrProdutos(Produto[] arrProdutos) {
    this.arrProdutos = arrProdutos;
  }

  public int getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(int quantidade) {
    this.quantidade = quantidade;
  }

  public double getFinanceiro() {
    return financeiro;
  }

  public void setFinanceiro(double financeiro) {
    this.financeiro = financeiro;
  }

  public double getPrecoMedio() {
    return precoMedio;
  }

  public void setPrecoMedio(double precoMedio) {
    this.precoMedio = precoMedio;
  }
}