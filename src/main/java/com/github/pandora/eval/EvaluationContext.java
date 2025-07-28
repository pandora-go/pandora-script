package com.github.pandora.eval;

import java.util.HashMap;
import java.util.Map;

public class EvaluationContext {

    private Map<String, Object> params;

    public EvaluationContext() {
        this.params = new HashMap<>();
    }

    public Object add(String key, Object value) {
        return params.put(key, value);
    }

    public Object get(String key) {
        return params.get(key);
    }
}
