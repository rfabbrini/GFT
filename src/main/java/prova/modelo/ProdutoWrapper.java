package prova.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProdutoWrapper {
  private Produto[] data;

  public Produto[] getData() {
    return data;
  }

  public void setData(Produto[] data) {
    this.data = data;
  }
}