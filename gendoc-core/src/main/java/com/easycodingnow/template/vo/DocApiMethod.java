package com.easycodingnow.template.vo;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public interface DocApiMethod extends DocApiMember {

     List<DocRequestParam> getRequestParams();


     String getReturnDesc();

     String getMethodName();

     String getFullClsName();
}
