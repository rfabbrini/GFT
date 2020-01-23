package prova.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import prova.modelo.Produto;
import prova.modelo.ProdutoWrapper;

@Component
public class FileService {

	@Autowired
	private DatabaseService databaseService;

	@Value("${json.files.path}")
	private String jsonFilesPath;
	@Value("${processed.files.path}")
	private String processedFilesPath;

	/** Processa Todos os arquivos Json */
	public void processarTodosArquivosJson() {
		try {
			//Inicia o paralelismo
			ExecutorService executor = Executors.newCachedThreadPool();

			//Verificar quais arquivos precisam ser processados
			List<Path> lFilePath = checkJsonArquivos().collect(Collectors.toList());

			//Processa apenas os arquivos que não foram processados
			for(Path filePath : lFilePath) {
					//Inicia um novo thread
					executor.submit(() -> {
						try{
							ProcessFileService processFileService = new ProcessFileService();
							ProdutoWrapper produtoWrapper = processFileService.processarArquivo(jsonFilesPath + filePath.getFileName().toString());							
							String[] newFileName = filePath.getFileName().toString().split(".json");

							for(int i=0; i < produtoWrapper.getData().length; i++) {
							// for(int i=0; i < 1; i++) {
								Produto produto = produtoWrapper.getData()[i];
								String id = String.format("%s%d", newFileName[0], i);

								databaseService.gravarProduto(id, produto);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					});	
				}
			// }	

			//Finaliza o processor e aguarda os processamentos terminarem
			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				System.out.println("All Files processed!!");
			} catch (InterruptedException e) {
				System.out.println("Error processing one of the files. Please Try Again");
			}

		} catch (IOException e) {
			System.out.println("IO generic Error");
			e.printStackTrace();
		}
	}

	/** Verifica quais arquivos json estão presentes */
	public Stream<Path> checkJsonArquivos() throws IOException {
		return Files.list(Paths.get(jsonFilesPath));
	}
}
