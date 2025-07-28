package com.github.pandora.loader;

import java.util.Date;

public class ScriptConfig {

    private final String name;
    private final String beanKlass;
    private final String md5;
    private final Date lastModified;
    private final String value;

    public ScriptConfig(String name, String beanKlass, String md5, Date lastModified, String value) {
        this.name = name;
        this.beanKlass = beanKlass;
        this.md5 = md5;
        this.lastModified = lastModified;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getMd5() {
        return md5;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getValue() {
        return value;
    }

    public String getBeanKlass() {
        return beanKlass;
    }
}
