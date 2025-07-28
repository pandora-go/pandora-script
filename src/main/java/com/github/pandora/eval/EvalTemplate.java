package com.github.pandora.eval;

import com.github.pandora.exception.EvaluationException;

public abstract class EvalTemplate implements Evaluator {

    @Override
    public EvaluationValue evaluate(EvaluationContext context) throws EvaluationException {
        EvaluationValue value = new EvaluationValue();

        value.setValue(eval(context.get("params")));

        return value;
    }

    // invoke stub for generated codes
    public abstract Object eval(Object param);
}
