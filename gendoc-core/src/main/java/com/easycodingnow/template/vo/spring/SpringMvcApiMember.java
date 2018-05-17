package com.easycodingnow.template.vo.spring;

import com.easycodingnow.reflect.*;
import com.easycodingnow.template.vo.DocApiMember;
import com.easycodingnow.template.vo.DocRequestParam;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public abstract class SpringMvcApiMember implements DocApiMember {

    protected Member member;

    private String apiName;

    private String apiDescription;

    private String requestMethod;

    private String requestPath;

    public boolean isPostJson() {
        return isPostJson;
    }

    private boolean isPostJson; //是否是json提交


    public SpringMvcApiMember(Member member) {
        this.member = member;

        //解析名称
        apiName = parseApiName();

        //解析描述
        apiDescription = parseApiDescription();

        //解析请求类型
        String requestType = parseRequestType();

        if("json".equals(requestType)){
            requestMethod = "POST(application/json)";
            isPostJson = true;
        }else{
            //解析请求方法
            requestMethod = parseRequestMethod();
        }

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
        Annotation annotation = member.getAnnotationByName("ApiOperation");
        if(annotation != null){
            if(NormalAnnotation.class.isInstance(annotation)){
                NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                return normalAnnotation.getValue("value");
            }
        }
        return member.getCommentName();
    }

    private String parseApiDescription(){
        //兼容swagger 优先返回swagger
        Annotation annotation = member.getAnnotationByName("ApiOperation");
        if(annotation != null){
            if(NormalAnnotation.class.isInstance(annotation)){
                NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                return normalAnnotation.getValue("value");
            }
        }

        return member.getCommentDesc();
    }

    private String parseRequestType(){
        if(member instanceof  Method){
            List<MethodParam> params = ((Method)member).getParams();
            if(!CollectionUtils.isEmpty(params)) {
                for (MethodParam methodParam : params) {
                    if(methodParam.getAnnotationByName("RequestBody") != null){
                        return "json";
                    }
                }
            }
        }
        return "form";
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
        String path = "";
        if(annotation != null){
            if(SingleAnnotation.class.isInstance(annotation)){
                path  = ((SingleAnnotation)annotation).getValue();
            }else if(NormalAnnotation.class.isInstance(annotation)){
                NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                path = normalAnnotation.getValue("value");

                if(path == null){
                    path = normalAnnotation.getValue("path");
                }
                if(path.contains("\"")){
                    path = path.substring(1);
                    path = path.substring(0, path.length()-1);
                    path = path.replace("\"","").trim();
                    if(path.contains(",")){
                        //说明有多个
                        path = "["+path+"]";
                    }
                }

            }
        }

        if(path.endsWith("/")){
            path = path.substring(0, path.length()-1);
        }

        return path;
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
