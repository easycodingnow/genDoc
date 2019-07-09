package com.easycodingnow.template.vo.rpc;

import com.easycodingnow.reflect.*;
import com.easycodingnow.template.vo.DocApiMember;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public abstract class RpcApiMember implements DocApiMember {

    protected Member member;

    private String apiName;

    private String apiDescription;

    private String requestMethod;

    private String requestPath;

    public boolean isPostJson() {
        return isPostJson;
    }

    private boolean isPostJson; //是否是json提交

    private String needLogin;

    @Override
    public String getNeedLogin() {
        if (StringUtils.isEmpty(needLogin)) {
            Annotation annotation = member.getAnnotationByName("Api");
            if(annotation != null){
                if(annotation instanceof NormalAnnotation){
                    NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                    needLogin = normalAnnotation.getValue("needLogin");
                }
            }
            needLogin = "false".equals(needLogin)?"否":"是";

        }

        return needLogin;
    }

    RpcApiMember(Member member) {
        this.member = member;

        //解析名称
        apiName = parseApiName();

        //解析描述
        apiDescription = parseApiDescription();

        //解析请求类型
        requestMethod = "POST(application/json)";
        isPostJson = false;

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

    /**
     * @path sdsd
     * @return
     */
    private String parseRequestPath(){
        //这里定义Api注解获取请求path,如果没有就取方法名称

        List<String> apiScanAnnotation = member.getGenConfig().getApiScanAnnotation();
        List<String> scanCommentTag = member.getGenConfig().getApiScanCommentTag();

        String path = "";

        List<Annotation> annotations = member.getAnnotations();
        if (!CollectionUtils.isEmpty(apiScanAnnotation) && !CollectionUtils.isEmpty(annotations)) {
            for (Annotation annotation:annotations) {
                if (apiScanAnnotation.contains(annotation.getName())) {
                    if(annotation instanceof NormalAnnotation){
                        NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                        path = normalAnnotation.getValue("path");
                    } else if (annotation instanceof SingleAnnotation) {
                        path = ((SingleAnnotation)annotation).getValue();
                    }

                    if(StringUtils.isNotEmpty(path) && path.contains("\"")){
                        path = path.substring(1);
                        path = path.substring(0, path.length()-1);
                        path = path.replace("\"","").trim();
                        if(path.contains(",")){
                            //说明有多个
                            path = "["+path+"]";
                        }
                    }
                    break;
                }

            }
        }

        if (!CollectionUtils.isEmpty(scanCommentTag) && member.getComment() != null
        && !CollectionUtils.isEmpty(member.getComment().getTags())) {
            for (Comment.Tag tag:member.getComment().getTags()) {
                if (scanCommentTag.contains(tag.getTagName())) {
                    path =  tag.getContent();
                    break;
                }
            }
        }

        if (StringUtils.isEmpty(path)) {
           if (member.getParentMember() != null) {
               path = member.getParentMember().getName() + "." + member.getName();
           }
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
