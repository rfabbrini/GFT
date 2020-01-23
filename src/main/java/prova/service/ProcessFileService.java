package prova.service;

import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import prova.modelo.ProdutoWrapper;

public class ProcessFileService {

	/** Processa um arquivo json */
	public ProdutoWrapper processarArquivo(String fileName) throws IOException {
		JsonReader reader = new JsonReader(new FileReader(fileName));
		try {
			Gson gson = new Gson();
			return gson.fromJson(reader, ProdutoWrapper.class);
		} finally {
			reader.close();
		}
	}
}
