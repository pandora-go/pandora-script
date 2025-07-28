package com.github.pandora.antlr;

import com.github.pandora.exception.ParseException;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class AstNodeErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object o, int i, int i1, String s, RecognitionException e) {
        throw new ParseException(String.format("from line %d to %d %s", i, i1, s));
    }
}
