package com.easycodingnow.demo.example;

import com.easycodingnow.GenConfig;
import com.easycodingnow.GenDoc;
import com.easycodingnow.gendoc.web.GenDocWebApp;
import com.easycodingnow.gendoc.web.GenWebConfig;

import java.util.Arrays;

/**
 * @author lihao
 * @since 2018/3/22
 */
public class Main {

    public static void main(String[] args) {
         genDoc(); //生成文档，
        //startWeb(); //启动文档服务器
    }

    /**
     * 启动内置的文档服务器
     */
    private static void startWeb(){
        new GenDocWebApp(new GenWebConfig()).start();
    }

    /**
     * 生成文档
     * 根据自己项目的情况去配置
     */
    private static void genDoc(){

        //项目的根目录
        String projectRootPath = "/Users/leeco/Desktop/github/gendoc";

        GenConfig genConfig = new GenConfig();
        genConfig.setOutputPath(projectRootPath+"/gendoc-demo/gendoc-demo-example/src/main/resources/gendoc/out/html");
        genConfig.setScanPackages(Arrays.asList("com.easycodingnow.demo.web1", "com.easycodingnow.demo.web2"));
        genConfig.setSourcePathRoot(Arrays.asList(
                projectRootPath + "/gendoc-demo/gendoc-demo-web1/src/main/java",
                projectRootPath + "/gendoc-demo/gendoc-demo-domain/src/main/java",
                projectRootPath + "/gendoc-demo/gendoc-demo-web2/src/main/java"
        ));

        GenDoc.gen(genConfig);
    }
}
