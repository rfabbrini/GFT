package prova;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import prova.api.SimulacaoController;
import prova.modelo.Produto;
import prova.modelo.ProdutoWrapper;
import prova.modelo.SimulacaoLojistaWrapper;

@SpringBootTest
class SimulacaoControllerTest {

	private String produtoTeste = "TESTEPRODUTO";

	@Test
	void dividirIgualmenteSimulacoes() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();

		SimulacaoController simulacaoController = new SimulacaoController();
		SimulacaoLojistaWrapper simulacaoLojistaWrapper = simulacaoController.iniciarSimulacoes(produtoWrapper, 4);

		assertThat(simulacaoLojistaWrapper.getSimulacoes().length).isEqualTo(4);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[0].getArrProdutos()[0].getQuantity()).isEqualTo(7);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[1].getArrProdutos()[0].getQuantity()).isEqualTo(7);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[2].getArrProdutos()[0].getQuantity()).isEqualTo(7);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[3].getArrProdutos()[0].getQuantity()).isEqualTo(7);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[0].getArrProdutos()[1].getQuantity()).isEqualTo(10);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[1].getArrProdutos()[1].getQuantity()).isEqualTo(10);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[2].getArrProdutos()[1].getQuantity()).isEqualTo(10);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[3].getArrProdutos()[1].getQuantity()).isEqualTo(10);
	}

	@Test
	void somarProdutos() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();

		SimulacaoController simulacaoController = new SimulacaoController();
		SimulacaoLojistaWrapper simulacaoLojistaWrapper = simulacaoController.iniciarSimulacoes(produtoWrapper, 4);
		int soma = simulacaoLojistaWrapper.getSimulacoes()[0].somarProdutos();

		assertThat(soma).isEqualTo(17);
	}
	
	@Test
	void calcularResto() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();

		SimulacaoController simulacaoController = new SimulacaoController();
		SimulacaoLojistaWrapper simulacaoLojistaWrapper = simulacaoController.iniciarSimulacoes(produtoWrapper, 4);
		int resto = simulacaoController.calcularResto(simulacaoLojistaWrapper, produtoWrapper.getData()[0]);

		assertThat(resto).isEqualTo(1);
	}

	@Test
	void recuperarProduto() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();

		SimulacaoController simulacaoController = new SimulacaoController();
		SimulacaoLojistaWrapper simulacaoLojistaWrapper = simulacaoController.iniciarSimulacoes(produtoWrapper, 4);

		Produto p = simulacaoController.recuperarProduto(simulacaoLojistaWrapper.getSimulacoes()[0], produtoWrapper.getData()[0]);

		assertThat(p).isEqualTo(produtoWrapper.getData()[0]);
	}

	@Test
	void dividirResto() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();

		SimulacaoController simulacaoController = new SimulacaoController();
		SimulacaoLojistaWrapper simulacaoLojistaWrapper = simulacaoController.iniciarSimulacoes(produtoWrapper, 4);
		simulacaoLojistaWrapper = simulacaoController.dividirResto(produtoWrapper, 4, simulacaoLojistaWrapper);

		assertThat(simulacaoLojistaWrapper.getSimulacoes().length).isEqualTo(4);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[0].getArrProdutos()[0].getQuantity()).isEqualTo(8);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[1].getArrProdutos()[0].getQuantity()).isEqualTo(7);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[2].getArrProdutos()[0].getQuantity()).isEqualTo(7);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[3].getArrProdutos()[0].getQuantity()).isEqualTo(7);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[0].getArrProdutos()[1].getQuantity()).isEqualTo(10);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[1].getArrProdutos()[1].getQuantity()).isEqualTo(11);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[2].getArrProdutos()[1].getQuantity()).isEqualTo(11);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[3].getArrProdutos()[1].getQuantity()).isEqualTo(11);
	}
	
	@Test
	void calcularValoresSimulacao() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();

		SimulacaoController simulacaoController = new SimulacaoController();
		SimulacaoLojistaWrapper simulacaoLojistaWrapper = simulacaoController.iniciarSimulacoes(produtoWrapper, 4);
		simulacaoLojistaWrapper = simulacaoController.dividirResto(produtoWrapper, 4, simulacaoLojistaWrapper);
		
		simulacaoLojistaWrapper.getSimulacoes()[0].cacularValores();
		simulacaoLojistaWrapper.getSimulacoes()[1].cacularValores();

		assertThat(simulacaoLojistaWrapper.getSimulacoes().length).isEqualTo(4);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[0].getQuantidade()).isEqualTo(18);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[0].getFinanceiro()).isEqualTo(105);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[0].getPrecoMedio()).isEqualTo(105.0 / 18);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[1].getQuantidade()).isEqualTo(18);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[1].getFinanceiro()).isEqualTo(106.5);
		assertThat(simulacaoLojistaWrapper.getSimulacoes()[1].getPrecoMedio()).isEqualTo(106.5 / 18);
	}

	private ProdutoWrapper mockProdutoWrapper() {
		Produto p1 = new Produto();
		p1.setProduct(produtoTeste);
		p1.setQuantity(29);
		p1.setIndustry("P1");
		p1.setOrigin("P1");
		p1.setPrice("$5.00");
		p1.setType("P1");
		Produto p2 = new Produto();
		p2.setProduct(produtoTeste);
		p2.setQuantity(43);
		p2.setIndustry("P2");
		p2.setOrigin("P2");
		p2.setPrice("$6.50");
		p2.setType("P2");
		ProdutoWrapper produtoWrapper = new ProdutoWrapper();
		produtoWrapper.setData(new Produto[]{p1, p2});
		return produtoWrapper;
	}
}
