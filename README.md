# GenDoc
Help you to generate API documents

通过注释生成api文档。
亮点和优势：
* **一键生成**，只需完善注释
* **支持泛型解析**，参数智能解析
* **不入侵代码**，**代码即文档**，完善代码的同时也生成文档，增加了代码可读性
* **兼容swagger**，之前接入swagger的项目，可以不做改动迁移(swagger只支持部分常用注解，如有需要支持部分，请提issue)
* 生成离线html文档，文档页面美观直接，支持接口搜索，实体信息查看，比传统的手写文档更加方便快捷。

# Quick Start
* git clone https://github.com/easycodingnow/genDoc.git
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

# 解析规则
* 类和方法的注释第一行默认为注释或者方法的名称，其他行为描述
* 注释添加#ignore#将会调过此类或者方法的解析

# 泛型解析
* 泛型解析比如`List<User> List<List<User>>  Map<String, User>  Map<String,List<User>>` 都只会解析出一个User对象，map集合只会解析value的值
* 如果返回值或者参数是类似于`Result<T> `或者`Object`这种无法解析的对象，可以在注释类指定解析类型

# 指定类型
* @return #type:com.domian.User,com.domian.Person#   在方法注释后面添加#type:...# 元信息可以生成返回值描述
* @param user  #type:com.domian.User,com.domian.Person# 在参数注释的 后面添加#type:...# 元信息可以生成请求实体描述

# 忽略解析
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

# 联系方式
* 如果你在使用过程中，有遇到任何问题或者疑问，可以随时提交issue.或者加入qq群交流
<img src="http://chuantu.xyz/t6/702/1559451388x2890211704.jpg" width = "270" height = "365" alt="图片名称" align=center />





