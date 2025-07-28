package com.github.pandora.loader;

import java.util.List;

public interface ScriptLoader {

    List<ScriptConfig> load();

    ScriptConfig load(String name);
}
