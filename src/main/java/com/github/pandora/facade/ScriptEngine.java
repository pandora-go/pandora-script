package com.github.pandora.facade;

public interface ScriptEngine {

    Object execute(String scriptName, Object param);

    Object execute(String scriptName, byte[] param, SerializeType type);

    Object execute(String scriptName, String param, SerializeType type);

    Object execute(String scriptName);
}
