package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.algaworks.junit.blog.negocio.EditorTestData.*;
import static java.math.BigDecimal.TEN;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorTest {

    @Spy
    Editor editor = new Editor(null, "Alex", "alex@gmail.com", TEN, true);

    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);

    @Mock
    ArmazenamentoEditor armazenamentoEditor;

    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    @InjectMocks
    CadastroEditor cadastroEditor;

    @Nested
    class CadastroComEditorValido{
        @BeforeEach
        void init(){
            Mockito.when(armazenamentoEditor.salvar(any(Editor.class)))
                    .thenAnswer(invocacao -> {
                        Editor editorPassado = invocacao.getArgument(0, Editor.class);
                        editorPassado.setId(1L);
                        return editorPassado;
                    });
        }
        @Test
        void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro(){
            Editor editorSalvo = cadastroEditor.criar(editor);
            assertEquals(1L, editorSalvo.getId());
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento(){
            cadastroEditor.criar(editor);
            Mockito.verify(armazenamentoEditor, times(1))
                    .salvar(Mockito.eq(editor));
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_e_Lancar_exception_ao_salvar_Entao_nao_deve_enviar_email(){
            Mockito.when(armazenamentoEditor.salvar(editor))
                    .thenThrow(new RuntimeException());
            assertAll("Não deve enviar e-mail, quando lançar exception do armazenamento",
                    () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                    () -> Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(any())
            );
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_destino_ao_editor(){
            //ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);

            Editor editorSalvo = cadastroEditor.criar(editor);

            Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());

            Mensagem mensagem = mensagemArgumentCaptor.getValue();

            assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
        }

        @Test
        void Dado_um_editor_valido_quando_cadastrar_Entao_deve_verificar_o_email(){
            //editor = Mockito.spy(editor);
            cadastroEditor.criar(editor);

            Mockito.verify(editor, Mockito.atLeast(1)).getEmail();
        }

        @Test
        void Dado_um_editor_valido_quando_cadastrar_Entao_deve_lancar_exception(){
            Mockito.when(armazenamentoEditor.encontrarPorEmail("alex@gmail.com"))
                    .thenReturn(empty())
                    .thenReturn(of(editor));

            Editor editorComEmailExistente = new Editor(null, "Alex", "alex@gmail.com", TEN, true);
            cadastroEditor.criar(editor);
            assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
        }

        @Test
        void Dado_um_editor_valido_quando_cadastrar_Entao_deve_enviar_email_apos_salvar(){
            cadastroEditor.criar(editor);
            InOrder inOrder = inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
            inOrder.verify(armazenamentoEditor, times(1)).salvar(editor);
            inOrder.verify(gerenciadorEnvioEmail, times(1)).enviarEmail(any(Mensagem.class));
        }
    }

    @Nested
    class CadastroComEditorNull{
        @Test
        void Dado_um_editor_null_quando_cadastrar_Entao_deve_lancar_exception(){
            assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
            //garante que os demais métodos não foram chamados
            verify(armazenamentoEditor, never()).salvar(any());
            verify(gerenciadorEnvioEmail, never()).enviarEmail(any());
        }
    }

    @Nested
    class EdicaoComEditorValido{

        @Spy
        Editor editor = umEditorNovo();

        @BeforeEach
        void init(){
            Mockito.when(armazenamentoEditor.salvar(editor)).thenAnswer(i -> i.getArgument(0, Editor.class));
            Mockito.when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(of(editor));
        }

        @Test
        void Dado_um_editor_valido_Quando_editar_Entao_deve_alterara_editor_salvo(){
            Editor editorAtualizado = umEditorExistente();

            cadastroEditor.editar(editorAtualizado);
            verify(editor, times(1)).atualizarComDados(editorAtualizado);

            InOrder inOrder = inOrder(editor, armazenamentoEditor);
            inOrder.verify(editor).atualizarComDados(editorAtualizado);
            inOrder.verify(armazenamentoEditor).salvar(editor);
        }

    }

    @Nested
    class EdicaoComEditorInexistente{

        @Spy
        Editor editor = umEditorComIdInexistente();

        @BeforeEach
        void init(){
            Mockito.when(armazenamentoEditor.encontrarPorId(99L)).thenReturn(empty());
        }

        @Test
        void Dado_um_editor_valido_Quando_editar_Entao_deve_alterara_editor_salvo(){
            assertThrows(EditorNaoEncontradoException.class, () -> cadastroEditor.editar(editor));
            verify(armazenamentoEditor, never()).salvar(any(Editor.class));
        }

    }

}