package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrinho de Compra")
class CarrinhoCompraBDDTest {

    @Nested
    @DisplayName("Dado um carrinho de compras")
    class CarrinhoCompraAdicionarProduto{

        private final Cliente cliente = new Cliente(1L, "Ronaldo");
        private final CarrinhoCompra carrinhoCompra = new CarrinhoCompra(cliente, new ArrayList<>());
        private Produto produto;
        private Produto produtoExistenteEmCarroDeCompras;
        private int quantidadeDeUmProutoExistente;

        @BeforeEach
        void beforeEach(){

            produto = new Produto(6L, "Geladeira", "A Geladeira dos seus sonhos", valueOf(2500));
            Produto tv = new Produto(4L, "TV 55 Polegadas", "A TV dos seus sonhos", valueOf(8500));
            Produto iphone = new Produto(5L, "IPHONE 14 PRO MAX", "O IPHONE dos seus sonhos", valueOf(8500));
            Produto casaco = new Produto(7L, "Casaco", "O Casaco dos seus sonhos", valueOf(500));
            Produto pcGamer = new Produto(8L, "PC Gamer", "O PC Gamer dos seus sonhos", valueOf(10000));
            Produto notebook = new Produto(9L, "Notebook", "O Notebook dos seus sonhos", valueOf(3500));

            carrinhoCompra.adicionarProduto(tv, 3);
            carrinhoCompra.adicionarProduto(iphone, 2);
            carrinhoCompra.adicionarProduto(pcGamer, 2);
            carrinhoCompra.adicionarProduto(casaco, 4);
            carrinhoCompra.adicionarProduto(notebook, 3);

            produtoExistenteEmCarroDeCompras = pcGamer;
            quantidadeDeUmProutoExistente = carrinhoCompra.getItens().get(2).getQuantidade();
        }

        @AfterEach
        void afterEach(){
            carrinhoCompra.esvaziar();
        }

        //Adicionar

        @Nested
        @DisplayName("Quando adicionar um produto")
        class AdicionaProdutoValido {

            @Test
            @DisplayName("Então deve colocar um novo item no carrinho de compras")
            void deveAdicionarProdutoValido(){
                carrinhoCompra.adicionarProduto(produto, 1);
                assertEquals(6, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("E então quantidade total de itens deve ser aumentada em 1")
            void deveAumentarQuantidateDeItens(){
                carrinhoCompra.adicionarProduto(produto, 1);
                assertEquals(15, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }
        }

        @Nested
        @DisplayName("Quando adicionar um produto válido presente no carrinho de compras")
        class AdicionaProdutoValidoExistente{

            @Test
            @DisplayName("Então não deve aumentar a quantidade total de itens")
            void naodeveAumentarQuantiaDeUmProduto(){
                assertEquals(14, carrinhoCompra.getQuantidadeTotalDeProdutos());
            }
        }

        @Nested
        @DisplayName("Quando adicionar um produto nulo")
        class AdicionaProdutoInvalido{

            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void deveLancarIllegalArgumentException(){
                assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.adicionarProduto(null, 1));
            }
        }

        @Nested
        @DisplayName("Quando adicionar um produto com quantidade menor que 1")
        class AdicionaProdutoComQuantidadeInvalidada{

            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void deveLancarIllegalArgumentException(){
                assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.adicionarProduto(produto, 0));
            }

        }

        //Remover

        @Nested
        @DisplayName("Quando não encontrar produto para ser removido")
        class RemoveProdutoNaoEncontrado{

            @Test
            @DisplayName("Então deve lançar NoSuchElementException")
            void deveLancarNoSuchElementException(){
                Produto produtoInexistente = new Produto(10L, "Microondas", "o melhor microondas nacional", BigDecimal.valueOf(750));
                assertThrows(NoSuchElementException.class, () -> carrinhoCompra.removerProduto(produtoInexistente));
            }

        }

        @Nested
        @DisplayName("Quando remover produto nulo")
        class RemoveProdutoNulo{

            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void deveLancarIllegalArgumentException(){
                assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.removerProduto(null));
            }
        }

        @Nested
        @DisplayName("Quando remover produto presente no carrinho de compras")
        class RemoverProdutoValido{

            @Test
            @DisplayName("Então deve diminuir tamanho da lista de itens no carrinho")
            void deveDiminuirTamanhoDaLista(){
                carrinhoCompra.removerProduto(produtoExistenteEmCarroDeCompras);
                assertEquals(4, carrinhoCompra.getItens().size());
            }

            @Test
            @DisplayName("Então deve alterar conteudo da lista de itens no carrinho")
            void deveAlterarConteudoDaLista(){
                carrinhoCompra.removerProduto(produtoExistenteEmCarroDeCompras);
                List<ItemCarrinhoCompra> listaEsperada = carrinhoCompra.getItens();
                assertIterableEquals(listaEsperada, carrinhoCompra.getItens());
            }
        }

        //Aumentar quantidade de um produto

        @Nested
        @DisplayName("Quando aumentar quantidade de um produto nulo")
        class AumentarQuantidadeDeProduto{

            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void deveLancarIllegalArgumentException(){
                assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.aumentarQuantidadeProduto(null));
            }

        }

        @Nested
        @DisplayName("Quando aumentar quantidade de um produto que não está presente na lista de itens")
        class AumentarQuantidadeDeProdutoInexistente{

            Produto produtoInexistente = new Produto(10L, "Microondas", "o melhor microondas nacional", BigDecimal.valueOf(750));

            @Test
            @DisplayName("Então deve lançar NoSuchElementException")
            void deveLancarIllegalArgumentException(){
                assertThrows(NoSuchElementException.class, () -> carrinhoCompra.aumentarQuantidadeProduto(produtoInexistente));
            }

        }

        @Nested
        @DisplayName("Quando produto válido existente ser colocado no carrinho")
        class AumentaQuantidadeDeProdutoExistente{

            @Test
            @DisplayName("Então deve aumentar quantidade deste produto")
            void deveAumentarQuantidadeDoProduto(){
                carrinhoCompra.aumentarQuantidadeProduto(produtoExistenteEmCarroDeCompras);
                assertEquals(3, quantidadeDeUmProutoExistente + 1);
             }

        }

        //Diminuir quantidade de produto

        @Nested
        @DisplayName("Quando diminuir quantidade de um produto nulo")
        class DiminuiQuantidadeDeProdutoNulo{

            @Test
            @DisplayName("Então deve lançar IllegalArgumentException")
            void develancarIllegalArgumentException(){
                assertThrows(IllegalArgumentException.class, () -> carrinhoCompra.diminuirQuantidadeProduto(null));
            }

        }

        @Nested
        @DisplayName("Quando diminuir quantidade de um produto que não existe")
        class DiminuiQuantidadeDeProdutoInexistente{

            @Test
            @DisplayName("Então deve lançar NoSuchElementException")
            void develancarIllegalArgumentException(){
                Produto produtoInexistente = new Produto(10L, "Microondas", "o melhor microondas nacional", BigDecimal.valueOf(750));
                assertThrows(NoSuchElementException.class, () -> carrinhoCompra.diminuirQuantidadeProduto(produtoInexistente));
            }

        }

        @Nested
        @DisplayName("Quando produto válido ser colocado no carrinho")
        class DiminuiQuantidadeDeProduto{

            @Test
            @DisplayName("Então deve diminuir quantidade desse produto")
            void develancarIllegalArgumentException(){
                carrinhoCompra.diminuirQuantidadeProduto(produtoExistenteEmCarroDeCompras);
            }

        }

        //Verificar valor total de todos os itens na lista

        @Nested
        @DisplayName("Quando verificar valor total da compra")
        class VerificaValorTotalDoCarrinho{

            @Test
            @DisplayName("Então deve calcular a soma de todos os itens no carrinho")
            void develancarIllegalArgumentException(){
                assertEquals(BigDecimal.valueOf(75000), carrinhoCompra.getValorTotal());
            }

        }

    }

}