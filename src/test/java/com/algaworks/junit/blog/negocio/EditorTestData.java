package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import static java.math.BigDecimal.TEN;

public class EditorTestData {

    private EditorTestData(){

    }

    public static Editor umEditorNovo(){
        return new Editor(null, "Alex", "alex@gmail.com", TEN, true);
    }

    public static Editor umEditorExistente(){
        return new Editor(1L, "Alex", "alex@gmail.com", TEN, true);
    }

    public static Editor umEditorComIdInexistente(){
        return new Editor(99L, "Alex", "alex@gmail.com", TEN, true);
    }

}
