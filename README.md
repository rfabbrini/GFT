# Ler, Armazenar, Calcular Dados

Por: Rodrigo Fabbrini

A aplicação foi desenvolvida conforme as recomendações.
Todos os fontes e compilados encontran-se neste repositório

## Testes
A aplicação foi desenvolvida utilizando TDD. 
Todos os testes que eu utilizei para o desenvolvimento estão nos fontes na pasta "src/test/java/prova/".

## Inicialização
Quando a aplicação é iniciada, ela inicia o processamento dos arquivo json localizada na pasta "/json" em paralelo.

## Persistência
Os dados serão lidos e transformados em Objetos e consequentemente serão armazenados em uma base de dados local (H2).
A interrupção do processamento não impactará em nenhum erro. Quando o processo for retomado, apenas os dados novos serão adicionados a database.
(Como não há um ID único para cada registro, é necessário que os arquivos json tenham nomes unicos. Arquivos com nomes duplicados em sessão diferentes poderão não ser processados.)

## Inicialização
Como a aplicação já está compilada, em uma máquina com Java 8 instalado, basta rodar o seguinte comando no diretório raiz da aplicação.

```bash
java -jar target/prova.jar
```

## Endpoint
Uma vez que a aplicação está inicializada, o endpoint para calculo das simulações poderá ser chamado. 

```rest
/simular
  produto: Sigla do produto que será distribuído
  quantidade: Quantidade de Lojas
```

A aplicação está configurada para rodar na porta 8080.
Para chamar a aplicação, basta chamar chamar a URL diretamente no browser, ou enviando um GET por uma ferramenta como o Postman.

Exemplo de chamada:
```rest
http://localhost:8080/simular?produto=AGEN&quantidade=5
```

### Conclusão

Agradeço o tempo e a oportunidade.
Obrigado!
