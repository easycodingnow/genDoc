package com.easycodingnow;

import java.util.Arrays;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class Main {

    public static void main(String[] args) {
        GenDoc.gen(
                GenConfig.builder()
                        .genHtmlPath("/Users/leeco/Desktop/github/gendoc/gendoc-core/src/main/resources")
                        .scanPackages(Arrays.asList("com.easycodingnow.demo.web1", "com.easycodingnow.demo.web2"))
                        .sourcePathRoot(new String[]{
                                "/Users/leeco/Desktop/github/gendoc/gendoc-demo/gendoc-demo-web1/src/main/java",
                                "/Users/leeco/Desktop/github/gendoc/gendoc-demo/gendoc-demo-domain/src/main/java",
                                "/Users/leeco/Desktop/github/gendoc/gendoc-demo/gendoc-demo-web2/src/main/java"
                        }).build()
        );
    }
}
