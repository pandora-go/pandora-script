package com.github.pandora.antlr;

import com.github.pandora.antlr.generated.ScriptLexer;
import com.github.pandora.antlr.generated.ScriptParser;
import com.github.pandora.ast.ScriptNode;
import com.github.pandora.compile.CompileContext;
import com.github.pandora.compile.Compiler;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;
import java.io.InputStream;

public class AntlrCompiler implements Compiler {

    @Override
    public ScriptNode compile(String script, CompileContext compileContext) {
        ScriptLexer lexer = new ScriptLexer(CharStreams.fromString(script));
        return compileInner(lexer, compileContext);
    }

    @Override
    public ScriptNode compile(InputStream stream, CompileContext compileContext) {
        try {
            ScriptLexer lexer = new ScriptLexer(CharStreams.fromStream(stream));
            return compileInner(lexer, compileContext);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected ScriptNode compileInner(ScriptLexer lexer, CompileContext compileContext) {
        ScriptParser parser = new ScriptParser(new CommonTokenStream(lexer));
        parser.addErrorListener(new AstNodeErrorListener());
        parser.setBuildParseTree(true);
        ParseTree tree = parser.block();

        AstNodeVisitor visitor = new AstNodeVisitor(compileContext.getTargetClass());
        return visitor.visit(tree);
    }
}
