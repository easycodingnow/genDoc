# genDoc
Help you to generate API documents

通过注释方便的生成自定义的文档，对代码没有任何的入侵性，加快生产效率的小工具；
模板使用的freemarker，模板放在resources/template下，可以自定义生成模板。
目前有两种生成模式：
* 基于pojo类，实体描述文档
* 基于spring mvc的接口描述文档


## 生成实体描述文档
要生成文档的实体如下
```java
public class Person {

    private String name; //姓名


    private Integer age; //年龄


    private String desc; //描述
}
```

### 测试代码:
```java
  public static void main(String[] args) throws Exception {
        String sourcePath = ""; //代码文件的绝对路径
        Class cls =  Parse.parse(new FileInputStream(sourcePath)); //得到生成一个文档类的模型
        String str = TemplateParse.parseTemplate("pojo_doc_md.tmp", cls); //解析模板
        System.out.println(str);
    }
```
### 生成结果(这里用的是markdown模板):
| 名称 | 类型 | 描述 |
|:---:|:---:|:---:|
 | name | String |  姓名  |
| age | Integer |  年龄  |
| desc | String |  描述  |

## 生成spring mvc的接口描述文档
要生成的controller类如下:
```java
@RequestMapping
public class DemoController {
    /**
     * #ignore#
     * 获取用户信息
     * @return 白名单
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserInfo() {
        return null;
    }


    /**
     * 获取菜单列表
     * 这是一段方法描述但是没有任何意义
     * @return
     */
    @RequestMapping(value = "/getMenuList", method = {RequestMethod.POST,  RequestMethod.GET})
    @ResponseBody
    public Object getMenuList() {
        return null;
    }


    /**
     * 根据获取城市列表详情
     * 这是一段方法描述但是没有任何意义
     * @param ids  id列表
     * @param num  要获取的数量
     * @return
     */
    @RequestMapping(value = "/getCityList", method = RequestMethod.GET)
    @ResponseBody
    public Object getCityInfo(@RequestParam("id") String[] ids, @RequestParam(name = "num", defaultValue = "12") int num) {
        return null;
    }
}
```
### 测试代码:
```java
  public static void main(String[] args) throws Exception {
        String sourcePath = ""; //代码文件的绝对路径
        Class cls =  ConvertHelper.convertToSpringMvcClass(Parse.parse(new FileInputStream(sourcePath))); //得到生成一个文档类的模型
        String str = TemplateParse.parseTemplate("spring_mvc_api_doc.md.tmp", cls); //解析模板
        System.out.println(str);
    }
```
注: 
* 方法注释说明的第一行默认为方法名称，其他行为方法的描述。
* 如果在注释中添加#ignore#关键字，会调过此方法的解析

### 生成结果(这里用的是markdown模板):
- #### 获取菜单列表
* 请求地址:/getMenuList
* 描述:这是一段方法描述但是没有任何意义
* 请求方法:POST, GET
- ##### 参数列表
| 名称 | 类型 | 必填 | 默认值 | 描述 |
|:---: |:---:|:---: |:---: |:---:|
- #### 根据获取城市列表详情
* 请求地址:/getCityList
* 描述:这是一段方法描述但是没有任何意义
* 请求方法:GET
- ##### 参数列表
| 名称 | 类型 | 必填 | 默认值 | 描述 |
|:---: |:---:|:---: |:---: |:---:|
|id|String[]|是| |id列表
|num|int|否|12|要获取的数量




