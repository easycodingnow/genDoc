package com.easycodingnow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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

    private String[] sourcePathRoot;

    private String genHtmlPath;

}
