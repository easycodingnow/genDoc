package com.easycodingnow.template.vo.spring;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.vo.DocClass;
import com.easycodingnow.template.vo.DocMember;
import com.easycodingnow.template.vo.DocMethod;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class SpringMvcClass extends SpringMvcMember implements DocClass{
    private List<DocMethod> methods;

    private Class cls;

    public SpringMvcClass(List<DocMethod> methods, Class cls) {
        super(cls);
        this.methods = methods;
        this.cls = cls;
    }


    public List<DocMethod> getMethods() {
        return methods;
    }
}
