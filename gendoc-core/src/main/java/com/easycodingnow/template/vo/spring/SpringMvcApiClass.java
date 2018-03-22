package com.easycodingnow.template.vo.spring;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.vo.DocApiApiClass;
import com.easycodingnow.template.vo.DocApiMethod;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class SpringMvcApiClass extends SpringMvcApiMember implements DocApiApiClass {
    private List<DocApiMethod> methods;

    private Class cls;

    public SpringMvcApiClass(List<DocApiMethod> methods, Class cls) {
        super(cls);
        this.methods = methods;
        this.cls = cls;
    }


    public List<DocApiMethod> getMethods() {
        return methods;
    }
}
