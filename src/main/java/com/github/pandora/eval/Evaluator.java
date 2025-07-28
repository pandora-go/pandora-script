package com.github.pandora.eval;

import com.github.pandora.exception.EvaluationException;

public interface Evaluator {

    EvaluationValue evaluate(EvaluationContext context) throws EvaluationException;

}
