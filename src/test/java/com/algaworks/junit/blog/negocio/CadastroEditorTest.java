package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorTest {

    CadastroEditor cadastroEditor;
    ArmazenamentoFixoEmMemoria armazenamentoEditar = new ArmazenamentoFixoEmMemoria();
    Editor editor;

    @BeforeEach
    void beforeEach(){
        editor = new Editor(null, "Alex", "alex@gmail.com", BigDecimal.TEN, true);
        armazenamentoEditar = new ArmazenamentoFixoEmMemoria();
        cadastroEditor = new CadastroEditor(
                armazenamentoEditar,
                new GerenciadorEnvioEmail(){
                    @Override
                    public void enviarEmail(Mensagem mensagem){
                        System.out.println("Enviando mensagem para: "+ mensagem.getDestinatario());
                    }
                }
        );
    }

    @Test
    public void Dado_um_editor_valido_quando_criar_Entao_deve_retornar_um_id_de_cadastro(){
        Editor editorSalvo = cadastroEditor.criar(editor);
        assertEquals(1L, editorSalvo.getId());
        assertTrue(armazenamentoEditar.chamouSalvar);

    }

    @Test
    public void Dado_um_editor_null_quando_criar_Entao_deve_lancar_exception(){
        assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
        assertFalse(armazenamentoEditar.chamouSalvar);
    }

    @Test
    public void Dado_um_editor_com_email_existente_Quando_criar_Então_deve_lancar_exception(){
        editor.setEmail("alex.existe@gmail.com");
        assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editor));

    }

    @Test
    public void Dado_um_editor_com_email_existente_Quando_criar_Então_nao_deve_salvar(){
        editor.setEmail("alex.existe@gmail.com");
        try {
            cadastroEditor.criar(editor);
        } catch (RegraNegocioException e){
            assertFalse(armazenamentoEditar.chamouSalvar);
        }
    }

}