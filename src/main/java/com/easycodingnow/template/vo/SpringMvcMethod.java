package com.easycodingnow.template.vo;

import com.easycodingnow.reflect.*;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;
import sun.swing.BeanInfoUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class SpringMvcMethod extends Method {

    public SpringMvcMethod(){

    }

    public SpringMvcMethod(Method method){
        setParentMember(method.getParentMember());
        setPackageName(method.getPackageName());
        setName(method.getName());
        setType(method.getType());
        setComment(method.getComment());
        setAnnotations(method.getAnnotations());
        setModifier(method.getModifier());
        setParams(method.getParams());
    }

    public String getReturnDesc(){
        Comment comment = getComment();
        if(comment != null){
           Comment.Tag tag = comment.getTagByTagName("return");
           if(tag != null){
               return tag.getContent();
           }
        }

        return "";
    }


    public String getApiName(){
        String apiName = "";
        Comment comment = getComment();
        if(comment != null && StringUtils.isNotEmpty(comment.getDescription())){
             apiName = comment.getDescription().split("\n", 2)[0];
        }

        if(StringUtils.isEmpty(apiName)){
            apiName = getName();
        }

        return apiName;
    }

    public String getRequestMethod(){

        Annotation annotation = getAnnotationByName("RequestMapping");
        if(annotation != null){
            if(NormalAnnotation.class.isInstance(annotation)){
                NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                String method = normalAnnotation.getValue("method");

                return  method.replaceAll("(RequestMethod\\.)|\\{|\\}", "").trim();
            }
        }

        return "GET,POST";
    }

    public String getRequestPath(){
       Annotation annotation = getAnnotationByName("RequestMapping");
       if(annotation != null){
           if(MarkerAnnotation.class.isInstance(annotation)){
                return "";
           }else if(SingleAnnotation.class.isInstance(annotation)){
               return ((SingleAnnotation)annotation).getValue();
           }else if(NormalAnnotation.class.isInstance(annotation)){
               NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
               String path = normalAnnotation.getValue("value");

               if(path == null){
                   path = normalAnnotation.getValue("path");
               }
               return path;
           }
       }

       return "";
    }


    public String getApiDescription(){
        Comment comment = getComment();
        if(comment != null && StringUtils.isNotEmpty(comment.getDescription())){
            String[] strArr = comment.getDescription().split("\n", 2);
            return strArr.length > 1?strArr[1].trim():"";
        }

        return "";
    }

    public List<RequestParam> getRequestParam(){
        List<MethodParam> params = this.getParams();
        List<RequestParam>  requestParams = new ArrayList<RequestParam>();

        if(!CollectionUtils.isEmpty(params)){
            for(MethodParam methodParam:params){
                Annotation annotation  =  methodParam.getAnnotationByName("RequestParam");
                RequestParam requestParam = new RequestParam();

                requestParam.setType(methodParam.getType());

                if(annotation == null || MarkerAnnotation.class.isInstance(annotation)){

                    requestParam.setName(methodParam.getName());

                }else if(SingleAnnotation.class.isInstance(annotation)){

                    requestParam.setName(((SingleAnnotation)annotation).getValue());
                    requestParam.setRequired(true);

                }else if(NormalAnnotation.class.isInstance(annotation)){
                    NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;

                    requestParam.setName(normalAnnotation.getValue("name"));
                    requestParam.setDefaultValue(normalAnnotation.getValue("defaultValue"));
                }

                Comment comment = getComment();
                if(comment != null){
                    Comment.Tag tag = comment.getParamTagByName(methodParam.getName()); //通过方法参数名称获取
                    if(tag != null){
                        requestParam.setDescription(tag.getContent());
                    }
                }


                requestParams.add(requestParam);
            }

        }

        return requestParams;

    }


}
