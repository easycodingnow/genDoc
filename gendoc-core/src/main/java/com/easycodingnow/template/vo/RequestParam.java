package com.easycodingnow.template.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/9
 */
@Data
public class RequestParam {

    private String  name;

    private String type;  //参数类型

    private List<DocPojoClass> typeDoc; //对象类型的参数文档模型

    private String description;

    private String  defaultValue;

    private boolean isRequired;

}
