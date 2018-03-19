package com.easycodingnow.template.vo;

import com.easycodingnow.reflect.Annotation;
import com.easycodingnow.reflect.MarkerAnnotation;
import com.easycodingnow.reflect.NormalAnnotation;
import com.easycodingnow.reflect.SingleAnnotation;
import lombok.Data;

/**
 * @author lihao
 * @since 2018/3/9
 */
@Data
public class RequestParam {

    private String  name;

    private String type;

    private String description;

    private String  defaultValue;

    private boolean isRequired;

    private boolean isRequestBody;

}
