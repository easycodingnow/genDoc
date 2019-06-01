# GenDoc
Help you to generate API documents

通过注释生成api文档。
亮点和优势：
* 一键生成，只需完善注释
* 支持泛型解析，参数智能解析
* 不入侵代码，代码即文档，完善代码的同时也生成文档，增加了代码可读性
* 兼容swagger，之前接入swagger的项目，可以不做改动迁移(swagger只支持部分常用注解，如有需要支持部分，请提issue)
* 生成离线html文档，文档页面美观直接，支持接口搜索，实体信息查看，比传统的手写文档更加方便快捷。

# Quick Start
* clone 代码到本地
* 打开gendoc-demo/gendoc-demo-example/src/main/java/com/easycodingnow/demo/example/Main.java 类
``` java
    private static void genDoc(){

        String sourcePath = ""; //源代码目录
        String outPath = ""; //生成的文件输出目录
        GenDoc.gen(sourcePath, outPath);

    }
```
* 填写要扫描的源代码目录sourcePath
* 填写生成文档的输出目录outPath
* 运行main函数


# 如何集成到自己的项目
将example/Main.java  中的扫描的源码路径 和 包改成自己项目的。运行main方法即可生成。

# Tip
# 解析规则
* 类和方法的注释第一行默认为注释或者方法的名称，其他行为描述
* 注释添加#ignore#将会调过此类或者方法的解析

# 泛型解析
* 泛型解析比如List<User> List<List<User>>  Map<String, User>  Map<String,List<User>> 都只会解析出一个User对象，map集合只会解析value的值
* 如果返回值或者参数是类似于Result<T> 或者Object这种无法解析的对象，可以在注释类指定解析类型

# 指定解析类型
* @return #type:com.domian.User,com.domian.Person#   在方法注释后面添加#type:...# 元信息可以生成返回值描述
* @param user  #type:com.domian.User,com.domian.Person# 在参数注释的 后面添加#type:...# 元信息可以生成请求实体描述

# 配置需要忽略解析的参数
* 有的时候，一些方法参数不是具体的入参对象，不希望被解析到，可以在解析配置里面配置要忽略的参数
* GenConfig.ignoreApiAnnotationParam  如果参数有ignoreApiAnnotationParam里面的注解就会被忽略
* GenConfig.ignoreApiTypeParam  如果参数类型在ignoreApiTypeParam配置的列表里面就会被忽略



# 计划
* 目前只支持spring mvc和rpc类型项目。后面集合完善其他类型api的解析接入
* 丰富文档输出类型，多种样式的html文档，markdown，word文档等
* 在线调试api
* 提供在线文档服务，和git等版本管理工具结合

# 生成效果
![Alt](https://github.com/easycodingnow/genDoc/blob/master/doc.png)





