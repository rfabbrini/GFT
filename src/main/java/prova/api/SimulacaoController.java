package prova.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import prova.modelo.Produto;
import prova.modelo.ProdutoWrapper;
import prova.modelo.SimulacaoLojista;
import prova.modelo.SimulacaoLojistaWrapper;
import prova.service.DatabaseService;

@RestController
public class SimulacaoController {

  @Autowired
  private DatabaseService databaseService;

  @GetMapping("/simular")
	public SimulacaoLojistaWrapper greeting(
    @RequestParam(value = "produto") String produto,
    @RequestParam(value = "quantidade") Integer qtdeLojas) {

      List<Produto> listProduto = databaseService.recuperarProdutos(produto);
      ProdutoWrapper produtoWrapper = new ProdutoWrapper();
      produtoWrapper.setData(listProduto.stream().toArray(Produto[]::new));

      SimulacaoLojistaWrapper simulacaoLojistaWrapper = iniciarSimulacoes(produtoWrapper, qtdeLojas);
      simulacaoLojistaWrapper = dividirResto(produtoWrapper, qtdeLojas, simulacaoLojistaWrapper);
      simulacaoLojistaWrapper.calcularSimulacoes();

		return simulacaoLojistaWrapper;
  }
  
  /** Inicia as simulacoes com a divisao basica dos produtos */
  public SimulacaoLojistaWrapper iniciarSimulacoes(ProdutoWrapper produtoWrapper, Integer qtdeLojas) {
    SimulacaoLojista[] arrSimulacaoLojista = new SimulacaoLojista[qtdeLojas];
    for(int i = 0; i < qtdeLojas; i++) {
      SimulacaoLojista simulacaoLojista = new SimulacaoLojista();
      arrSimulacaoLojista[i] = simulacaoLojista;
      simulacaoLojista.setArrProdutos(new Produto[produtoWrapper.getData().length]);

      for(int t = 0; t < produtoWrapper.getData().length; t++) {
        Produto prod = produtoWrapper.getData()[t].copiarParcial(qtdeLojas);
        simulacaoLojista.getArrProdutos()[t] = prod;
      }
    }
        
    SimulacaoLojistaWrapper simulacaoLojistaWrapper = new SimulacaoLojistaWrapper();
    simulacaoLojistaWrapper.setSimulacoes(arrSimulacaoLojista);    
    return simulacaoLojistaWrapper;
  }

  public SimulacaoLojistaWrapper dividirResto(ProdutoWrapper produtoWrapper, Integer qtdeLojas, SimulacaoLojistaWrapper simulacaoLojistaWrapper) {
    if(simulacaoLojistaWrapper.getSimulacoes().length > 0) {
      //itera pelos produtos recuperados da base
      for(Produto p : produtoWrapper.getData()) {
        int resto = calcularResto(simulacaoLojistaWrapper , p);

        //divide o resto
        while(resto > 0) {
          SimulacaoLojista menorSoma = simulacaoLojistaWrapper.getSimulacoes()[0];
          for(SimulacaoLojista s : simulacaoLojistaWrapper.getSimulacoes()) {
            if(s.somarProdutos() < menorSoma.somarProdutos()) {
              menorSoma = s;
            }
          }

          //recupera o produto da simulacao e acrescenta uma unidade
          Produto prod = recuperarProduto(menorSoma, p);
          prod.setQuantity(prod.getQuantity() + 1);

          //calcula o resto novamente
          resto = calcularResto(simulacaoLojistaWrapper , p);
        }
      }
    }

    return simulacaoLojistaWrapper;
  }

  /** Recupera um produto dentro de uma simulacao */
  public Produto recuperarProduto(SimulacaoLojista simulacaoLojista, Produto produto) {
    Produto retorno = null;
    for(Produto p : simulacaoLojista.getArrProdutos()) {
      if(p.equals(produto)) {
        retorno = p;
        break;
      }
    }
    return retorno;
  }

  /** Calcula o resto para um determinado produto */
  public Integer calcularResto(SimulacaoLojistaWrapper simulacaoLojistaWrapper, Produto produto) {
    int soma = 0;
    for(SimulacaoLojista s : simulacaoLojistaWrapper.getSimulacoes()) {
      Produto p = recuperarProduto(s, produto);
      soma += p.getQuantity();
    }
    return produto.getQuantity() - soma;
  }

}