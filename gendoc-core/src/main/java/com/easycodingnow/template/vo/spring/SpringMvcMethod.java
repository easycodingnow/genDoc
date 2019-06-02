package com.easycodingnow.template.vo.spring;

import com.easycodingnow.GenConfig;
import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.*;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.vo.DocApiMethod;
import com.easycodingnow.template.vo.DocParseHelp;
import com.easycodingnow.template.vo.DocPojoClass;
import com.easycodingnow.template.vo.DocRequestParam;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lihao
 * @since 2018/3/9
 */
public class SpringMvcMethod extends SpringMvcApiMember implements DocApiMethod {

    private List<DocRequestParam> requestParams;

    private String returnDesc;


    private List<DocPojoClass> returnTypes;


    public SpringMvcMethod(Method member) {
        super(member);

        //解析请求参数
        requestParams = parseRequestParams();

        //解析返回数据描述
        returnDesc = parseReturnDesc();

        //解析返回数据类型
        returnTypes = parseReturnType();
    }

    public List<DocRequestParam> getRequestParams() {
        return requestParams;
    }


    public String getReturnDesc() {
        return returnDesc;
    }


    public List<DocPojoClass> getReturnTypes() {
        return returnTypes;
    }

    private Comment.Tag getReturnTag(){
        Comment comment = member.getComment();
        if(comment != null){
            return  comment.getTagByTagName("return");

        }

        return null;
    }

    private List<DocPojoClass> parseReturnType(){
        Comment.Tag returnTag = getReturnTag();

        if(returnTag != null){
            List<DocPojoClass> docPojoClassList = new ArrayList<DocPojoClass>();

            if(!CollectionUtils.isEmpty(returnTag.getMetaData()) && returnTag.getMetaData().containsKey("type")){
                String[] paramTypes = returnTag.getMetaData().get("type").split(",");

                for(String paramType:paramTypes){
                    Class paramClass = Parse.parse(paramType.trim(), member.getGenConfig());
                    if(paramClass != null){
                        docPojoClassList.add(new DocPojoClass(paramClass));
                    }
                }

                return docPojoClassList;
            }

        }

        //智能寻找
        Class paramClass = Parse.autoParse(member);
        if(paramClass != null){
            return Lists.newArrayList(new DocPojoClass(paramClass));
        }

        return null;
    }


    private String parseReturnDesc(){
        Comment.Tag returnTag = getReturnTag();

        if(returnTag != null){
            return returnTag.getContent();
        }

        return "";
    }

    private boolean skipMethodParam(MethodParam methodParam, Comment.Tag tag){
        if("HttpServletResponse".equals(methodParam.getType()) ||
                "HttpServletRequest".equals(methodParam.getType())){
            return true;
        }

        List<String> ignoreAnnotations = member.getGenConfig().getIgnoreApiAnnotationParam();
        if(!CollectionUtils.isEmpty(ignoreAnnotations) && !CollectionUtils.isEmpty(methodParam.getAnnotations())){
            List<String> annotations = methodParam.getAnnotations().stream().map((Annotation::getName)).collect(Collectors.toList());
            for(String ignoreAnnotation:ignoreAnnotations){
                if(annotations.contains(ignoreAnnotation)){
                    return true;
                }
            }
        }


        List<String> ignoreTypeParam = methodParam.getGenConfig().getIgnoreApiTypeParam();
        if(!CollectionUtils.isEmpty(ignoreTypeParam)){
            if(ignoreTypeParam.contains(methodParam.getType())){
                return true;
            }
        }



        //other skip condition
        if(tag != null && StringUtils.isNotEmpty(tag.getContent()) && tag.getContent().contains("#ignore#")){
            return true;
        }

        return false;
    }

    private List<DocRequestParam> parseRequestParams(){
        List<MethodParam> params = ((Method)member).getParams();
        List<DocRequestParam>  requestParams = new ArrayList<DocRequestParam>();

        if(!CollectionUtils.isEmpty(params)){
            for(MethodParam methodParam:params){
                Annotation annotation  =  methodParam.getAnnotationByName("RequestParam");
                if(annotation == null){
                    annotation = methodParam.getAnnotationByName("PathVariable");
                }

                Comment.Tag paramTag =   member.getComment()!=null?member.getComment().getParamTagByName(methodParam.getName()):null;

                if(annotation == null && skipMethodParam(methodParam, paramTag)){
                    //跳过 非api参数的解析
                    continue;
                }

                DocRequestParam requestParam = new DocRequestParam();

                requestParam.setType(methodParam.getType());

                if(annotation == null || annotation instanceof MarkerAnnotation){

                    requestParam.setName(methodParam.getName());

                }else if(annotation instanceof SingleAnnotation){

                    requestParam.setName(((SingleAnnotation)annotation).getValue());
                    requestParam.setRequired(true);

                }else if(annotation instanceof NormalAnnotation){
                    NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;

                    String isRequire = normalAnnotation.getValue("required");

                    requestParam.setRequired(StringUtils.isEmpty(isRequire) || isRequire.equals("true"));

                    requestParam.setName(normalAnnotation.getValue("name"));

                    if(StringUtils.isEmpty(requestParam.getName())){
                        requestParam.setName(normalAnnotation.getValue("value"));
                    }
                    requestParam.setDefaultValue(normalAnnotation.getValue("defaultValue"));
                }

                if(paramTag != null){
                    DocParseHelp.parseMethodParamTag(methodParam, paramTag, requestParam);
                } else {
                    //智能寻找
                    Class paramClass = Parse.autoParse(methodParam);
                    if(paramClass != null){
                        requestParam.setTypeDoc(Lists.newArrayList(new DocPojoClass(paramClass)));
                    }
                }


                requestParams.add(requestParam);
            }

        }

        return requestParams;

    }


}
