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
    - ![alt text for screen readers](/3_9_.png "Text to show on mouseover")


### 4 Stub, Mock e Spy

#### 4.1. Implementações falsas com Stub

Stubs ou dublês são objetos falsos para simular partes de objetos reais de forma que 
implementam o minímo necessário para que o código de teste funcione. 
- Exemplo: na classe de teste CadastroEditorTest fora feitos quatros testes:
  - Testar cadastro de um editor: neste teste foi preciso criar um implementação de ArmazenamentoEditor para simular
apenas o Id de editor no momento em que foi salvo. O teste "Dado_um_editor_valido_quando_criar_Entao_deve_retornar_um_id_de_cadastro"
válida apenas o Id do objeto que foi "salvo". Foi simulado um editor salvo de forma que a única propriedade a ser retornada
foi um id, que era o minímo necessário para passar no teste.
  - o teste
    ````java
    @Test
    public void Dado_um_editor_valido_quando_criar_Entao_deve_retornar_um_id_de_cadastro(){
        Editor editorSalvo = cadastroEditor.criar(editor);
        assertEquals(1L, editorSalvo.getId());
        assertTrue(armazenamentoEditar.chamouSalvar);

    }        
    ````
  - objeto simulado em uma implementação da interface ArmazenamentoEditor para ter a propriedade miníma necessária para executar.
  - ````java
    @Override
    public Editor salvar(Editor editor) {
        chamouSalvar = true;
        if(editor.getId() == null){
            editor.setId(1L);
        }
        return editor;
    }  
    ````
    
#### 4.2. Introdução ao Mock

- Mocks são imitações ou unidades falsas que simulam o comportamento de unidades reais.
A diferença para um stub é que enquanto o Mock valida o **comportamento** de uma parte
da aplicação o Stub valida apenas o **estado** de um dado para fazer um teste funcionar.
- Vantagens:
  - Não é necessário **implementar** uma interface ou **extender** uma **classe**.
  - Facilidade de definir um comportamento **fictício dinâmico** em uma classe
  - Verificar comportamento da classe com mock

#### 4.5. Alterando estado dos parâmetros passados no mock

#### 4.6. Parâmetros dinâmicos

- com o método thenAnswer é posspivel customizar um comportamento. Através uma função lambda que recebe
um objeto 'invocacao' como argumento, no caso um objeto do tipo Editor 

- Mockito.any: útil quando há cenários com diversos parâmetros nos métodos mockados e para cenários
em que o valor do argumento não é importante para o teste.

#### 4.7. Verificando chamada de métodos com mock usando Mockito verify
  - ````java
    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento(){
        cadastroEditor.criar(editor);
        Mockito.verify(armazenamentoEditor, Mockito.times(1))
                .salvar(Mockito.eq(editor));
    }
    ````
  - o método verify é usada para saber se os métodos utilizados no mock foram chamados.
  - Mockito.times(1) -> o número de vezes que o método é esperado. 

#### 4.8. Forçando uma Exception com mock

#### 4.9. Capturando parâmetros enviados aos mocks com Argument Captor
- Argument Captor: permite capturar objetos passados como argumentos em métodos de cenário de testes para válidar
informações neles.
- ````java
    @Test
    void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor(){
        ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);

        Editor editorSalvo = cadastroEditor.criar(editor);

        Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

        Mensagem mensagem = mensagemArgumentCaptor.getValue();

        assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
    }
   ````

- no trecho de código **Mockito.verify(gerenciadorEnvioEmail)
.enviarEmail(mensagemArgumentCaptor.capture());**  é feita a captura do argumento que é um Tipo <Mensagem>
definida logo no início do método. Em seguida é extraído do objeto captor o objeto mensagem
e é feita a asserção.
 - também é possível utilizar ArgumentCaptor via anotação. Basta declarar juntas as classes mockadas
definidas nas linhas inicias da classe de teste igual foi delclarada no método e colocar a anotação
**@Captor**.
- ````java
   @Captor
   ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);
  ````
- Sua aplicação é útil em cenários de validação de lógica internas onde um método ao ser chamado
faz outras chamadas de métodos durante a sua execução.