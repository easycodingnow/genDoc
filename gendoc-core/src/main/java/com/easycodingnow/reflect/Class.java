package com.easycodingnow.reflect;

import lombok.Data;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public class Class extends Member{

    private List<Field> fields;

    private List<Method> methods;


}
