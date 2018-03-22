package com.easycodingnow.reflect;


import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class Method  extends Member{

    private List<MethodParam> params;

    public List<MethodParam> getParams() {
        return params;
    }

    public void setParams(List<MethodParam> params) {
        this.params = params;
    }
}
