package com.easycodingnow.template.vo;


import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class DocField {


    private String name;

    private String desc;

    private String type;

    private List<DocPojoClass> typeDoc; //对象类型的参数文档模型

    private boolean require;

    private String defaultValue;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRequire() {
        return require;
    }

    public void setRequire(boolean require) {
        this.require = require;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List<DocPojoClass> getTypeDoc() {
        return typeDoc;
    }

    public void setTypeDoc(List<DocPojoClass> typeDoc) {
        this.typeDoc = typeDoc;
    }

}
