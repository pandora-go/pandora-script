package com.github.pandora.compile;

import com.github.pandora.ast.ScriptNode;

import java.io.InputStream;

public interface Compiler {

    ScriptNode compile(String script, CompileContext compileContext);

    ScriptNode compile(InputStream stream, CompileContext compileContext);
}
