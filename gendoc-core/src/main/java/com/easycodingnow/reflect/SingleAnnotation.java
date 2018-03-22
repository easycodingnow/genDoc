package com.easycodingnow.reflect;

;

/**
 * @author lihao
 * @since 2018/3/8
 */
public  class SingleAnnotation extends Annotation{
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
