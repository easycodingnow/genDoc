package com.easycodingnow.reflect;


import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class Class extends Member{

    private List<Field> fields;

    private List<Method> methods;

    private List<Class> innerClass; //内部类

    private Class parentClass; //父类


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

    public List<Class> getInnerClass() {
        return innerClass;
    }

    public void setInnerClass(List<Class> innerClass) {
        this.innerClass = innerClass;
    }

    public Class getParentClass() {
        return parentClass;
    }

    public void setParentClass(Class parentClass) {
        this.parentClass = parentClass;
    }
}
