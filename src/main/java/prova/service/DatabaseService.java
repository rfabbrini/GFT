package prova.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import prova.modelo.Produto;

@Component
public class DatabaseService {

	@Autowired
	private JdbcTemplate jtm;
	
	/** Grava um produto na base */
	public synchronized void gravarProduto(String id, Produto produto) {
		try {
			String sql = String.format("INSERT INTO tProduto (id, product, quantity, price, type, industry, origin) VALUES ('%s', '%s', %d, '%s', '%s', '%s', '%s')",
				id, produto.getProduct(), produto.getQuantity(), produto.getPrice(), produto.getType(), produto.getIndustry(), produto.getOrigin());
			jtm.update(sql);			
		} catch(DuplicateKeyException e) {
			//Caso o processamento durante um arquivo tenha sido interrompido, chaves duplicadas não serão processadas
		}
	}

	public List<Produto> recuperarProdutos(String produto) {
		String sql = String.format("SELECT * FROM tProduto where product='%s'", produto);
		return jtm.query(sql, new BeanPropertyRowMapper<>(Produto.class));
	}
}
