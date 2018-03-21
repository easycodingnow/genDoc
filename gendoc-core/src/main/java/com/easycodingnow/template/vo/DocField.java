package com.easycodingnow.template.vo;

import lombok.Data;
import lombok.Getter;

/**
 * @author lihao
 * @since 2018/3/21
 */
@Data
public class DocField {


    private String name;

    private String desc;

    private String type;

    private boolean require;

    private String defaultValue;


}
