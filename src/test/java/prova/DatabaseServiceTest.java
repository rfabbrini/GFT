package prova;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.google.gson.Gson;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import prova.modelo.Produto;
import prova.modelo.ProdutoWrapper;
import prova.service.DatabaseService;

@SpringBootTest
class DatabaseServiceTest {

	@Autowired
	private JdbcTemplate jtm;
	@Autowired
	private DatabaseService databaseService;

	private String idTeste = "TESTEID";
	private String produtoTeste = "TESTEPRODUTO";

	@AfterEach
	void limparDatabase() {
		removerIdsTeste();
	}

	@Test
	void testInjection() {
		assertThat(jtm).isNotNull();
		assertThat(databaseService).isNotNull();
	}

	@Test
	void testGravarProduto() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();
		databaseService.gravarProduto(idTeste, produtoWrapper.getData()[0]);

		List<Produto> list = readMockFromDatabase();
		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	void testGravarProdutoDuplicado() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();
		databaseService.gravarProduto(idTeste, produtoWrapper.getData()[0]);
		databaseService.gravarProduto(idTeste, produtoWrapper.getData()[0]);

		List<Produto> list = readMockFromDatabase();
		assertThat(list.size()).isEqualTo(1);
	}
	
	@Test
	void testRecuperarProdutos() {
		ProdutoWrapper produtoWrapper = mockProdutoWrapper();
		databaseService.gravarProduto(idTeste, produtoWrapper.getData()[0]);

		List<Produto> list = databaseService.recuperarProdutos(produtoTeste);

		assertThat(list.size()).isEqualTo(1);
	}

	private ProdutoWrapper mockProdutoWrapper() {
		String json = String.format("{\"data\":[{\"product\":\"%s\",\"quantity\":25,\"price\":\"$0.67\",\"type\":\"3XL\",\"industry\":\"Industrial Specialties\",\"origin\":\"LA\"}]}", produtoTeste);
		return new Gson().fromJson(json, ProdutoWrapper.class);
	}
	
	private List<Produto> readMockFromDatabase() {
		String sql = String.format("SELECT * FROM tProduto where id='%s'", idTeste);
		return jtm.query(sql, new BeanPropertyRowMapper<>(Produto.class));
	}

	private void removerIdsTeste() {
		String sql = String.format("DELETE FROM tProduto WHERE id ='%s'", idTeste);
		jtm.update(sql);
	}
}
