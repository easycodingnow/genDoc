package com.easycodingnow.reflect;

import lombok.Data;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public abstract class Annotation {

    private Member member;

    private String name;


}
