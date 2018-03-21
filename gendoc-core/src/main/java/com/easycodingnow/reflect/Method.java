package com.easycodingnow.reflect;

import lombok.Data;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public class Method  extends Member{

    private List<MethodParam> params;

}
