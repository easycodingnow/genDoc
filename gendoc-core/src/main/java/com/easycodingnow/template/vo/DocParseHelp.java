package com.easycodingnow.template.vo;

import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Comment;
import com.easycodingnow.reflect.Member;
import com.easycodingnow.reflect.MethodParam;
import com.easycodingnow.utils.CollectionUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2019-06-02
 */
public class DocParseHelp {




    static public List<DocPojoClass> autParseParamType(Comment.Tag tag, Member member){
        if(tag != null){
            List<DocPojoClass> docPojoClassList = new ArrayList<DocPojoClass>();

            if(!CollectionUtils.isEmpty(tag.getMetaData()) && tag.getMetaData().containsKey("type")){
                String[] paramTypes = tag.getMetaData().get("type").split(",");

                for(String paramType:paramTypes){
                    Class paramClass = Parse.autoParse(paramType.trim(), member);
                    if(paramClass != null){
                        docPojoClassList.add(new DocPojoClass(paramClass));
                    }
                }

                return docPojoClassList;
            }
        }

        Class paramClass = Parse.autoParse(member);
        if(paramClass != null){
            return Lists.newArrayList(new DocPojoClass(paramClass));
        }

        return null;

    }
}
