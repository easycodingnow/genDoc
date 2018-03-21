package com.easycodingnow.template.vo.spring;

import com.easycodingnow.reflect.*;
import com.easycodingnow.template.vo.DocMember;
import com.easycodingnow.utils.StringUtils;

/**
 * @author lihao
 * @since 2018/3/21
 */
public abstract class SpringMvcMember implements DocMember{

    protected Member member;

    private String apiName;

    private String apiDescription;

    private String requestMethod;

    private String requestPath;


    public SpringMvcMember(Member member) {
        this.member = member;

        //解析名称
        apiName = parseApiName();

        //解析描述
        apiDescription = parseApiDescription();

        //解析请求方法
        requestMethod = parseRequestMethod();

        //解析请求地址
        requestPath = parseRequestPath();


        if(StringUtils.isEmpty(apiName)){
            //如果名称不存在，则进行兼容，尝试获取请求地址名称，如果没有设置则获取类名或者方法名作为名称

            if(!StringUtils.isEmpty(requestPath)){
                String[] pathArr = requestPath.split("/");
                apiName = pathArr[pathArr.length - 1];
            }else{
                apiName = member.getName();
            }
        }
    }

    private String parseApiName(){
        return member.getCommentName();
    }

    private String parseApiDescription(){
        return member.getCommentDesc();
    }


    private String parseRequestMethod(){

        Annotation annotation = member.getAnnotationByName("RequestMapping");
        if(annotation != null){
            if(NormalAnnotation.class.isInstance(annotation)){
                NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                String method = normalAnnotation.getValue("method");

                return StringUtils.isEmpty(method)?"GET, POST":method.replaceAll("(RequestMethod\\.)|\\{|\\}", "").trim();
            }
        }

        return "GET,POST";
    }

    private String parseRequestPath(){
        Annotation annotation = member.getAnnotationByName("RequestMapping");
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


    public String getApiName() {
        return apiName;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }


}
