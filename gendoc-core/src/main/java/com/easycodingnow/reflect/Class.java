package com.easycodingnow.reflect;


import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class Class extends Member{

    private List<Field> fields;

    private List<Method> methods;


    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
}
