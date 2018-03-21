package com.easycodingnow.template.vo;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public interface DocMethod extends DocMember{

     List<RequestParam> getRequestParams();


     String getReturnDesc();
}
