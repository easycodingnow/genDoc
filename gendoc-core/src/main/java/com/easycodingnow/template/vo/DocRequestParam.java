package com.easycodingnow.template.vo;


import java.util.List;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class DocRequestParam {

    private String  name;

    private String type;  //参数类型

    private List<DocPojoClass> typeDoc; //对象类型的参数文档模型

    private String description;

    private String  defaultValue;

    private boolean isRequired;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DocPojoClass> getTypeDoc() {
        return typeDoc;
    }

    public void setTypeDoc(List<DocPojoClass> typeDoc) {
        this.typeDoc = typeDoc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}
