package com.easycodingnow.demo.example;

import com.easycodingnow.GenConfig;
import com.easycodingnow.GenDoc;
import com.easycodingnow.gendoc.web.GenDocWebApp;
import com.easycodingnow.gendoc.web.GenWebConfig;
import com.google.common.collect.Lists;

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


        GenConfig genConfig = new GenConfig();
        genConfig.setApiScanCommentTag(Lists.newArrayList("apiNote"));
        genConfig.setSourcePath("/Users/lihao/Desktop/java/genDoc/gendoc-demo/gendoc-demo-web1");
        genConfig.setOutputPath("/Users/lihao/Desktop/java/genDoc/gendoc-demo/gendoc-demo-web1");
        genConfig.setWebType(GenConfig.WebType.SPRING_MVC);
        genConfig.setOutPutType(GenConfig.OutPutType.RPC_HTML);
        GenDoc.gen(genConfig);
    }
}
