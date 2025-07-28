package com.github.pandora;

import com.github.pandora.antlr.AntlrCompiler;
import com.github.pandora.ast.ScriptNode;
import com.github.pandora.compile.CompileContext;
import com.github.pandora.compile.Compiler;
import com.github.pandora.utils.ClassLoaderUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

public class CompilerTest {

    @Test
    public void compileTest() {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile("assert(3 > 1)", new CompileContext());
        Assert.assertNotNull(node);
    }

    //hard to trigger IOException
    //@Test(expected = IOException.class)
    public void compileExpTest() {
        InputStream stream = ClassLoaderUtils.getClassLoader().getResourceAsStream("nothing");
        Compiler compiler = new AntlrCompiler();
        compiler.compile(stream, new CompileContext());
    }

    @Test(expected = NullPointerException.class)
    public void compileNullPointerExpTest() {
        InputStream stream = ClassLoaderUtils.getClassLoader().getResourceAsStream("nothing");
        Compiler compiler = new AntlrCompiler();
        compiler.compile(stream, new CompileContext());
    }
}
