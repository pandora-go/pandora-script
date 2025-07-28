package com.github.pandora;

import com.github.pandora.antlr.AntlrCompiler;
import com.github.pandora.ast.ScriptNode;
import com.github.pandora.compile.CompileContext;
import com.github.pandora.compile.Compiler;
import com.github.pandora.exception.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class SyntaxTest {

    @Test(expected = ParseException.class)
    public void syntaxErr1Test() {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile("assert(3 >)", new CompileContext());
        Assert.assertNotNull(node);
    }

    @Test(expected = ParseException.class)
    public void syntaxErr2Test() {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile("assert(> 1)", new CompileContext());
        Assert.assertNotNull(node);
    }

    @Test(expected = ParseException.class)
    public void syntaxErr3Test() {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile("assert(>= 1)", new CompileContext());
        Assert.assertNotNull(node);
    }

    @Test(expected = ParseException.class)
    public void syntaxErr4Test() {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile("assert(1 >=)", new CompileContext());
        Assert.assertNotNull(node);
    }

    @Test(expected = ParseException.class)
    public void syntaxErr5Test() {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile("a = 1", new CompileContext());
        Assert.assertNotNull(node);
    }

    @Test(expected = ParseException.class)
    public void syntaxErr6Test() {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile("assert(a >= 1)", new CompileContext());
        Assert.assertNotNull(node);
    }
}
