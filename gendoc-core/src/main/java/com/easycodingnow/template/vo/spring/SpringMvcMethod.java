package com.easycodingnow.template.vo.spring;

import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.*;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.vo.DocApiMethod;
import com.easycodingnow.template.vo.DocPojoClass;
import com.easycodingnow.template.vo.DocRequestParam;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        //机器返回数据描述
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
                    Class paramClass = Parse.parse(paramType.trim(), member.getSourceRoot());
                    if(paramClass != null){
                        docPojoClassList.add(new DocPojoClass(paramClass));
                    }
                }

                return docPojoClassList;
            }

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
                "HttpServletRequest".equals(methodParam.getType()) ){
            return true;
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

                if(annotation == null || MarkerAnnotation.class.isInstance(annotation)){

                    requestParam.setName(methodParam.getName());

                }else if(SingleAnnotation.class.isInstance(annotation)){

                    requestParam.setName(((SingleAnnotation)annotation).getValue());
                    requestParam.setRequired(true);

                }else if(NormalAnnotation.class.isInstance(annotation)){
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
                    requestParam.setDescription(paramTag.getContent());

                    Map<String, String> metaMap =  paramTag.getMetaData();
                    if(metaMap != null && metaMap.containsKey("type")){
                        //注释中包含参数文档类型，进行参数文档的解析
                        List<DocPojoClass> docPojoClassList = new ArrayList<DocPojoClass>();
                        String[] paramTypes = metaMap.get("type").split(",");
                        for(String paramType:paramTypes){
                            Class paramClass = Parse.parse(paramType.trim(), member.getSourceRoot());
                            if(paramClass != null){
                                docPojoClassList.add(new DocPojoClass(paramClass));
                            }
                        }
                        requestParam.setTypeDoc(docPojoClassList);
                    }
                }


                requestParams.add(requestParam);
            }

        }

        return requestParams;

    }


}
