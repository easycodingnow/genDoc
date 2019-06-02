package com.easycodingnow.template.vo.rpc;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.vo.DocApiApiClass;
import com.easycodingnow.template.vo.DocApiMethod;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class RpcApiClass extends RpcApiMember implements DocApiApiClass {
    private List<DocApiMethod> methods;

    private Class cls;

    public RpcApiClass(List<DocApiMethod> methods, Class cls) {
        super(cls);
        this.methods = methods;
        this.cls = cls;
    }


    public List<DocApiMethod> getMethods() {
        return methods;
    }
}
