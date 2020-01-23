package prova;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import prova.modelo.ProdutoWrapper;
import prova.service.FileService;
import prova.service.ProcessFileService;

@SpringBootTest
class FileServiceTest {
		
	@Autowired
	FileService fileService;

	@Value("${json.files.path}") 
	private String jsonFilesPath;
	@Value("${processed.files.path}") 
	private String processedFilesPath;

	private String arquivoTeste = "TESTE.teste";

	@AfterEach
	void apagarArquivos() throws Exception {
		deleteMockJsonFile();
	}

	@Test
	void testInjection() {
		assertThat(fileService).isNotNull();
	}
	
	@Test
	void testLeituraJson() throws Exception {		
		mockJsonFile();

		Stream<Path> stPath = fileService.checkJsonArquivos();
		List<Path> lPathFiltered = stPath.filter(p -> p.getFileName().toString().equals(arquivoTeste)).collect(Collectors.toList());
		
		deleteMockJsonFile();

		assertThat(lPathFiltered.size()).isEqualTo(1);
	}
	
	@Test
	void testLerArquivo() throws IOException {
		mockJsonFile();
		ProcessFileService processFileService = new ProcessFileService();
		ProdutoWrapper produtoWrapper = processFileService.processarArquivo(jsonFilesPath + arquivoTeste);
		assertThat(produtoWrapper.getData().length).isGreaterThan(0);
	}

	private synchronized void mockJsonFile() throws IOException {
		File file = new File(jsonFilesPath + arquivoTeste);
		file.createNewFile();

		String json = "{\"data\":[{\"product\":\"RTIX\",\"quantity\":25,\"price\":\"$0.67\",\"type\":\"3XL\",\"industry\":\"Industrial Specialties\",\"origin\":\"LA\"},{\"product\":\"UTX\",\"quantity\":82,\"price\":\"$4.84\",\"type\":\"S\",\"industry\":\"Aerospace\",\"origin\":\"TX\"}]}";
		FileWriter fw = new FileWriter(file.getAbsolutePath());    
		fw.write(json);    
		fw.close();    
	}

	private synchronized void deleteMockJsonFile() throws IOException {
		File file = new File(jsonFilesPath + arquivoTeste);
		file.delete();
	}
}
