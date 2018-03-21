package com.easycodingnow.gendoc.web;

import com.easycodingnow.gendoc.web.parse.WebTemplateParse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class ContextHolder {

    private static ContextHolder contextHolder = new ContextHolder();


    @Getter
    @Setter
    private GenConfig genConfig;



    public static ContextHolder getInstance(){
        return contextHolder;
    }

}
