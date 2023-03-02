<div style="text-align: justify">

# Anotações do curso
### 3.3. Formatando nomes de testes com @DisplayNameGeneration: 
Faz a remoção do caracterece _ 
por um espaço no lugar em nomes de métodos, no momento da execução do teste. Se a anotação **@DisplayName**
estiver presente no método então @DisplayNameGeneration não conseguira alterar o nome do método na hora da exibição.


### 3.4. Aplicando a nomenclatura do BDD para nomear métodos de teste

Given, When, Then -> Dado, Quando, Então -> consiste em uma forma de padronizar nomes de métodos 
de testes, nesse caso, consiste em colocar no métodos estas três para dar maior legibilidade sobre
o que é feito no méotdo

### 3.5. Organizando classe de testes com sub-classes e @Nested

@Nested informa ao Junit que há testes unitários para serem executados na inner class, caso contrário Junit não encontraria. Resulado: 

![alt text for screen readers](/3_5_.png "Text to show on mouseover").

### 3.6. Preparando o cenário de testes com @BeforeEach e @BeforeAll

- **@BeforeAll**: Executa um método ANTES de TODOS os métodos de teste.
- **@BeforeEach**: Executa um método ANTES de CADA teste.
- **@AfterAll**: Executa um método DEPOIS de TODOS os métodos de teste.
- **@AfterEach**: Executa um método DEPOIS de CADA teste.

3.7. Um teste deve ter uma única asserção?

- Teste com uma asserção deixa claro o que o teste está validando, se quebrar falha apenas em uma asserção.
- Em testes com muitas asserções, caso uma falhe as demais não executam. Pode-se usar o assertAll para contornar isso,
mas furamente tomaria mais tempo para entender o que o código de teste valida
- Métodos também devem seguir regras de códigos limpo

</div>