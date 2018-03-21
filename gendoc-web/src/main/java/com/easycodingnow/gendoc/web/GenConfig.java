package com.easycodingnow.gendoc.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
@AllArgsConstructor
@Builder
@Data
public class GenConfig {

    private List<String> scanPackages;

    private Boolean  enabled = true;

    private String  contextPath = "genDoc";

    private Integer port = 9086;

    private String sourcePathRoot;

    private String genHtmlPath;


}
