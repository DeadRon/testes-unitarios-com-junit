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

<center>

![alt text for screen readers](/3_5_.png "Text to show on mouseover").

</center>

### 3.6. Preparando o cenário de testes com @BeforeEach e @BeforeAll

- **@BeforeAll**: Executa um método ANTES de TODOS os métodos de teste.
- **@BeforeEach**: Executa um método ANTES de CADA teste.
- **@AfterAll**: Executa um método DEPOIS de TODOS os métodos de teste.
- **@AfterEach**: Executa um método DEPOIS de CADA teste.

### 3.7. Um teste deve ter uma única asserção?

- Teste com uma asserção deixa claro o que o teste válida, se quebrar, falha apenas uma asserção.
- Em testes com muitas asserções, caso uma falhe as demais não executam. Pode-se usar o assertAll para contornar isso,
mas furamente tomaria mais tempo para entender o que o código de teste valida
- Métodos também devem seguir regras de código limpo

### 3.8. Combinando @Nested e @BeforeEach com a nomenclatura do BDD

- **Given/Dado(Vermelho)**: É declarada na classe de teste e representado por uma Inner class anotada com @Nested e @DisplayName("Nome do cenário"). Na Inner Class deve 
ser declarado um método anotado com @BeforeEach que terá a implementação do cenário base para os testes.

- **When/Quando(Verde)**: Declara-se uma Inner class dentro da Inner Class citada acima. Também deve estar com @Nested
para o JUnit ser informado de que há testes unitários nesta classe para serem executados e @DisplyName para descrever 
a ação a ser feita 

- **Then/Então(Branco)**: é o método de teste dentro da Inner Classe mais interna citada acima. Deve estar anotada com @Test
e @Displayname

<center>

![alt text for screen readers](/3_8_.png "Text to show on mouseover").

</center>

Resultado da execução dos testes:

<center>

![alt text for screen readers](/3_8_1.png "Text to show on mouseover").

</center>

</div>

### 3.9. Desafio - Implemente a lógica e testes de um carrinho de compras

Escrita de cenários de teste para a Classe CarrinhoCompra utilizando nomenclatura BDD
- Dado um carrinho de compras

  - Adiciona Produto válido
    - Quando adicionar um produto válido
      - Então deve colocar um novo item no carrinho de compras
      - E então quantidade total de itens deve ser aumentada em 1
    - Quando produto válido existente ser colocado no carrinho
      - Então deve aumentar quantidade deste produto
  - Adiciona Produto válido existente
    - Quando adicionar um produto válido presente no carrinho de compras
      - Então deve aumentar a quantidade desse produto
      - E não deve aumentar a quantidade total de itens em 1
  - Adiciona Produto inválido
    - Quando adicionar um produto nulo
      - Então deve lançar IllegalArgumentException
    - Quando adicionar um produto com quantidade menor que 1
      - Então deve lançar IllegalArgumentException
  - RemoveProduto    
    - Quando não encontrar produto para ser removido
      - Então deve lançar NoSuchElementException
    - Quando remover produto nulo
      - Então deve lançar IllegalArgumentException
    - Quando remover produto válido
      - Então deve diminuir tamanho da lista de itens no carrinho
  - Aumentar quantidade de produtos
    - Quando aumentar quantidade de um produto nulo
      - Então deve lançar IllegalArgumentException
    - Quando aumentar quantidade de um produto que não existe
      - Então deve lançar NoSuchElementException
    - Quando produto válido ser colocado no carrinho
      - Então deve aumentar quantidade desse produto
  - Diminuir quantidade
    - Quando diminuir quantidade de um produto nulo
      - Então deve lançar IllegalArgumentException
    - Quando diminuir quantidade de um produto que não existe
      - Então deve lançar NoSuchElementException
    - Quando produto válido ser colocado no carrinho
      - Então deve diminuir quantidade desse produto
  - Verificar valor total de todos os itens na lista
    - Quando verificar valor total da compra
      - Então deve calcular a soma de todos os itens no carrinho
  - RESULTADOS
    - ![alt text for screen readers](/3_9.png "Text to show on mouseover")