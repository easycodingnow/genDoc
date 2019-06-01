# GenDoc
Help you to generate API documents

通过注释生成api文档。
亮点和优势：
* 一键生成，不需要任何开发量，只要补充完善自己的注释即可
* 支持spring mvc各种注解配置
* 兼容swagger的部分注解，如果之前已经接过swagger，swagger部分不用做任何更改即可接入
* 智能查找参数和返回值类型
* 支持参数的泛型解析
* 通过注释生成文档，对代码没有任何入侵性
* 注释即文档，生成文档必须书写规范的代码注释，提高了代码的可读性。
* 生成离线html文档，文档页面美观直接，支持接口搜索，实体信息查看，比传统的手写文档更加方便快捷。
* 可扩展性强，模型分层设计，可以自定义输出结果，可以自行扩展解析能力

# Quick Start
* clone 代码到本地
* 打开gendoc-demo/gendoc-demo-example/src/main/java/com/easycodingnow/demo/example/Main.java 类
``` java
    private static void genDoc(){

        //项目的根目录，根据自己机器情况配置
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

# 参数智能查询
* 所以的参数对象和返回对象都会在扫描包下面智能搜索

# 泛型解析
* 泛型解析比如List<User> List<List<User>>  Map<String, User>  Map<String,List<User>> 都只会解析出一个User对象，map集合只会解析value的值
* 如果返回值或者参数是类似于Result<T> 或者Object这种无法解析的对象，可以在注释类指定解析类型

# 指定解析类型
* @return #type:com.domian.User,com.domian.Person#   在方法注释后面添加#type:...# 元信息可以生成返回值描述
* @param user  #type:com.domian.User,com.domian.Person# 在参数注释的 后面添加#type:...# 元信息可以生成请求实体描述



# 计划
* 目前只支持spring mvc类型web项目（后面会陆续支持各种类型web项目的生成，如果你有需求可自行改造，提交pr）
* 丰富文档输出类型，多种样式的html文档，makrdown，word文档
* 在线调试api

# 生成效果
![Alt](https://github.com/easycodingnow/genDoc/blob/master/doc.png)





