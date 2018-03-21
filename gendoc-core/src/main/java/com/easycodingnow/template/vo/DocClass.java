package com.easycodingnow.template.vo;

import com.easycodingnow.template.vo.spring.SpringMvcMethod;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public interface DocClass extends DocMember{
     List<DocMethod> getMethods();
}
