package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.exception.PostNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static java.time.OffsetDateTime.now;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastroPostTest {

    @Mock
    private ArmazenamentoPost armazenamentoPost;

    @Mock
    private CalculadoraGanhos calculadoraGanhos;

    @Mock
    private GerenciadorNotificacao gerenciadorNotificacao;

    @Captor
    private ArgumentCaptor<Notificacao> argumentCaptor = forClass(Notificacao.class);

    @InjectMocks
    private CadastroPost cadastroPost;

    private Post post;
    private Ganhos ganhos;
    private Editor autor;

    @BeforeEach
    void init(){
        ganhos = CadastroPostTestData.ganhos().build();
        autor = CadastroPostTestData.editor().build();
        post = CadastroPostTestData.post().build();
    }

    @Nested
    @DisplayName("Dado um post")
    class CadastraPost{

        @Nested
        @DisplayName("Quando cadastrar um post")
        class CadatrarPost{

            @Test
            @DisplayName("Entao post deve ser salvo")
            public void cadastraPost(){
                when(calculadoraGanhos.calcular(post)).thenReturn(ganhos);
                when(armazenamentoPost.salvar(post)).thenReturn(CadastroPostTestData.postSalvo().build());
                Post postCriado = cadastroPost.criar(post);

                verify(calculadoraGanhos, times(1)).calcular(post);
                verify(armazenamentoPost, times(1)).salvar(post);
                verify(gerenciadorNotificacao, times(1)).enviar(argumentCaptor.capture());

                Notificacao notificacao = argumentCaptor.getValue();
                String notificacaoEsperada = "Novo post criado -> " + post.getTitulo();

                assertEquals(notificacaoEsperada, notificacao.getConteudo());
                assertEquals(1L, postCriado.getId());
            }

        }

        @Nested
        @DisplayName("Quando remover um post")
        class RemoverPost{

            @Test
            @DisplayName("Entao post deve ser removido")
            public void RemovePost(){
                Post post = CadastroPostTestData.postSalvo().id(2L).build();
                post.setPago(false);
                post.setPublicado(false);

                when(armazenamentoPost.encontrarPorId(2L)).thenReturn(of(post));
                doNothing().when(armazenamentoPost).remover(2L);

                cadastroPost.remover(2L);

                verify(armazenamentoPost, times(1)).encontrarPorId(2L);
                verify(armazenamentoPost, times(1)).remover(2L);
            }
        }

        @Nested
        @DisplayName("Quando remover um post não encontrado")
        class RemoverPostNaoEncontrado{

            @Test
            @DisplayName("Entao post deve lancar PostNaoEncontradoException")
            public void NaoRemovePostInexistente(){

                when(armazenamentoPost.encontrarPorId(2L)).thenThrow(new PostNaoEncontradoException());

                assertAll("Não deve remover quando post não ser encontrado",
                        () -> assertThrows(PostNaoEncontradoException.class, () -> cadastroPost.remover(2L)),
                        () -> verify(armazenamentoPost, never()).remover(2L)
                );
            }
        }

        @Nested
        @DisplayName("Quando remover um post publicado")
        class RemoverPostPublicado{

            @Test
            @DisplayName("Entao post deve lancar RegraNegocioException")
            public void NaoRemovePostPublicado(){
                var post = CadastroPostTestData.postPublicadoENaoPago().id(2L).build();
                when(armazenamentoPost.encontrarPorId(2L)).thenReturn(of(post));

                RegraNegocioException regraNegocioException = assertThrows(RegraNegocioException.class, () -> cadastroPost.remover(2L));

                assertAll("Não deve remover quando post estiver publicado",
                        () -> verify(armazenamentoPost, times(1)).encontrarPorId(2L),
                        () -> verify(armazenamentoPost, never()).remover(2L),
                        () -> assertEquals("Um post publicado não pode ser removido", regraNegocioException.getMessage())
                );
            }
        }

        @Nested
        @DisplayName("Quando remover um post pago")
        class RemoverPostPago{

            @Test
            @DisplayName("Entao post deve lancar RegraNegocioException")
            public void NaoRemovePostPublicado(){
                var post = CadastroPostTestData.postPagoENaoPublicado().id(2L).build();
                //post.setPublicado(false);
                when(armazenamentoPost.encontrarPorId(2L)).thenReturn(of(post));

                RegraNegocioException regraNegocioException = assertThrows(RegraNegocioException.class, () -> cadastroPost.remover(2L));

                assertAll("Não deve remover quando post estiver publicado",
                        () -> verify(armazenamentoPost, times(1)).encontrarPorId(2L),
                        () -> verify(armazenamentoPost, never()).remover(2L),
                        () -> assertEquals("Um post pago não pode ser removido", regraNegocioException.getMessage())
                );
            }
        }

        @Nested
        @DisplayName("Quando editar um post inexistente")
        class EditarPost{

            @Test
            @DisplayName("Entao post deve lancar PostNaoEncontradoException")
            public void DeveEditarPost(){

                post = CadastroPostTestData.postInexistente().id(200L).build();

                when(armazenamentoPost.encontrarPorId(post.getId())).thenReturn(Optional.empty());

                assertThrows(PostNaoEncontradoException.class, () -> cadastroPost.editar(post));

                assertAll("Não deve editar quando post não existir",
                        () -> verify(armazenamentoPost, times(1)).encontrarPorId(200L),
                        () -> verify(calculadoraGanhos, never()).calcular(post),
                        () -> verify(armazenamentoPost, never()).salvar(post)
                );
            }
        }

        @Nested
        @DisplayName("Quando editar um post nao pago")
        class EditarPostPago{

            @Test
            @DisplayName("Entao post deve editar com calculo de ganhos")
            public void DeveEditarPost(){
                post = CadastroPostTestData.postNaoPagoENaoPublicado().id(1l).pago(false).build();

                when(armazenamentoPost.encontrarPorId(post.getId())).thenReturn(of(post));
                cadastroPost.editar(post);

                assertAll("Deve editar quando post existir",
                        () -> verify(armazenamentoPost, times(1)).encontrarPorId(post.getId()),
                        () -> verify(calculadoraGanhos, times(1)).calcular(post),
                        () -> verify(armazenamentoPost, times(1)).salvar(post)
                );
            }
        }

        @Nested
        @DisplayName("Quando editar um post pago")
        class EditarPostNaoPago{

            @Test
            @DisplayName("Entao post não deve editar com calculo de ganhos")
            public void NaoDeveEditarPost(){
                post = CadastroPostTestData.postNaoPagoENaoPublicado().id(1L).pago(true).build();

                when(armazenamentoPost.encontrarPorId(post.getId())).thenReturn(of(post));
                cadastroPost.editar(post);

                assertAll("Deve editar quando post existir",
                        () -> verify(armazenamentoPost, times(1)).encontrarPorId(post.getId()),
                        () -> verify(calculadoraGanhos, never()).calcular(post),
                        () -> verify(armazenamentoPost, times(1)).salvar(post)
                );
            }
        }

    }

    private Post postSalvo(){
        return new Post(1L, "Algo sobre a vida", "Reflexões", autor, "algo", ganhos, true, true);
    }

    private Notificacao notificacao(){
        Notificacao notificacao = new Notificacao(now(), "Novo post criado -> " + post.getTitulo());
        return notificacao;
    }

}
