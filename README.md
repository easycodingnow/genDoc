# genDoc
Help you to generate API documents

通过注释生成api文档。
亮点和优势：
* 一键生成，几乎不需要任何开发量
* 通过注释生成文档，对代码没有任何入侵性
* 注释即文档，生成文档必须书写规范的代码注释，提高了代码的可读性。
* 生成离线html文档，文档页面美观直接，支持接口搜索，实体信息查看，比传统的手写文档更加方便快捷。

# quick start
* clone 代码到本地
* 打开gendoc-demo/gendoc-demo-example/src/main/java/com/easycodingnow/demo/example/Main.java 类
``` java
    private static void genDoc(){

        //项目的根目录
        String projectRootPath = "";
        
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
```
* 修改projectRootPath变量，改成当前项目的根目录
* 运行main方法
* 用浏览器打开gendoc-demo/gendoc-demo-example/src/main/resources/gendoc/out/html/doc.html


# 如何集成到自己的项目
将example/Main.java  中的扫描的源码路径 和 包改成自己项目的。运行main方法即可生成。

# tip
* 类和方法的注释第一行默认为注释或者方法的名称，其他行为描述
* 注释添加#ignore#将会调过此类或者方法的解析
* @return #type:com.domian.User,com.domian.Person#   在方法注释后面添加#type:...# 元信息可以生成返回值描述
* @param user  #type:com.domian.User,com.domian.Person# 在参数注释的 后面添加#type:...# 元信息可以生成请求实体描述

# 计划
* 目前只支持spring mvc类型web项目（后面会陆续支持各种类型web项目的生成，如果你有需求可自行改造，提交pr）
* 丰富文档输出类型，多种样式的html文档，makrdown，word文档
* 在线调试api

# 演示







