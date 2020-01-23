package prova.modelo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Produto {
  private String product;
  private Integer quantity;
  private String price;
  private String type;
  private String industry;
  private String origin;

  public Produto copiarParcial(int multQtde) {
    Produto produto = new Produto();
    produto.setProduct(product);
    produto.setQuantity(quantity/multQtde);
    produto.setPrice(price);
    produto.setType(type);
    produto.setIndustry(industry);
    produto.setOrigin(origin);
    return produto;
  }

  @Override
  public boolean equals(Object o) { 
    Produto p = (Produto)o;
    return product.equals(p.getProduct()) 
      && price.equals(p.getPrice()) 
      && type.equals(p.getType()) 
      && industry.equals(p.getIndustry()) 
      && origin.equals(p.getOrigin()) 
      ? true : false;
  }

  public String getProduct() {
    return product;
  }

  public void setProduct(String product) {
    this.product = product;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }


}