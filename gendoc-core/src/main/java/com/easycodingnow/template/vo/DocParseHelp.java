package com.easycodingnow.template.vo;

import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Comment;
import com.easycodingnow.reflect.MethodParam;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2019-06-02
 */
public class DocParseHelp {


    static  public  void parseMethodParamTag(MethodParam methodParam, Comment.Tag paramTag, DocRequestParam requestParam ){
        requestParam.setDescription(paramTag.getContent());

        Map<String, String> metaMap =  paramTag.getMetaData();
        if(metaMap != null && metaMap.containsKey("type")){
            //注释中包含参数文档类型，进行参数文档的解析
            List<DocPojoClass> docPojoClassList = new ArrayList<DocPojoClass>();
            String[] paramTypes = metaMap.get("type").split(",");
            for(String paramType:paramTypes){
                Class paramClass = Parse.parse(paramType.trim(), methodParam.getGenConfig());
                if(paramClass != null){
                    docPojoClassList.add(new DocPojoClass(paramClass));
                }
            }
            requestParam.setTypeDoc(docPojoClassList);
        } else {
            //智能寻找
            Class paramClass = Parse.autoParse(methodParam);
            if(paramClass != null){
                requestParam.setTypeDoc(Lists.newArrayList(new DocPojoClass(paramClass)));
            }
        }
    }
}
