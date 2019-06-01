package com.easycodingnow.template.vo;

import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.*;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class DocPojoClass {
    private Class cls;

    private List<DocField> fields = new ArrayList<DocField>();

    private String type;



    private String fullType;

    private String desc;


    public DocPojoClass(Class cls) {
        this.cls = cls;

        this.type = cls.getName();
        this.fullType = cls.getPackageName()+"."+cls.getName();
        this.desc = cls.getCommentName();

        List<Field> fields = cls.getFields();

        if(!CollectionUtils.isEmpty(fields)){
            for(Field field:fields){
                DocField docField = new DocField();
                String name = field.getName();

                if(!CollectionUtils.isEmpty(field.getModifier()) && field.getModifier().contains("static")){
                    //过滤类变量
                    continue;
                }


                Annotation jsonFieldAt = field.getAnnotationByName("JSONField");
                if(jsonFieldAt != null){
                    if(jsonFieldAt instanceof SingleAnnotation){
                        name = ((SingleAnnotation) jsonFieldAt).getValue();
                    }else if(jsonFieldAt instanceof NormalAnnotation){
                        name = ((NormalAnnotation) jsonFieldAt).getValue("name");
                        String serialize = ((NormalAnnotation) jsonFieldAt).getValue("serialize");
                        if("false".equals(serialize)){
                            continue;
                        }
                    }
                }

                Annotation jacksonField = field.getAnnotationByName("JsonProperty");
                if(jacksonField != null){
                    if(jacksonField instanceof SingleAnnotation){
                        name = ((SingleAnnotation) jacksonField).getValue();
                    }else if(jacksonField instanceof NormalAnnotation){
                        name = ((NormalAnnotation) jacksonField).getValue("value");
                    }
                }

                Annotation gsonField = field.getAnnotationByName("SerializedName");
                if(gsonField != null){
                    if(gsonField instanceof SingleAnnotation){
                        name = ((SingleAnnotation) gsonField).getValue();
                    }else if(gsonField instanceof NormalAnnotation){
                        name = ((NormalAnnotation) gsonField).getValue("value");
                    }
                }

                docField.setName(name);
                //兼容swagger 优先返回swagger
                Annotation annotation = field.getAnnotationByName("ApiModelProperty");
                if(annotation != null){
                    if(NormalAnnotation.class.isInstance(annotation)){
                        NormalAnnotation normalAnnotation = (NormalAnnotation)annotation;
                        docField.setDesc(normalAnnotation.getValue("value"));
                        if(StringUtils.isEmpty(docField.getDesc())){
                            docField.setDesc(normalAnnotation.getValue("notes"));
                        }
                    }
                }else{
                    docField.setDesc(field.getComment()!=null?field.getComment().getDescription():"");
                }
                docField.setType(field.getType());

                if(field.getComment() != null){
                    Map<String, String> metaMap =  field.getComment().getMetaData();
                    if(metaMap != null && metaMap.containsKey("type")){
                        //注释中包含参数文档类型，进行参数文档的解析
                        List<DocPojoClass> docPojoClassList = new ArrayList<DocPojoClass>();
                        String[] paramTypes = metaMap.get("type").split(",");
                        for(String paramType:paramTypes){
                            paramType = paramType.trim();
                            Class paramClass = null;
                            if(paramType.contains("$")){
                                //内部类
                                String[] classArr = paramType.split("\\$");
                                if(classArr.length == 2){
                                    String publicClass = classArr[0];
                                    String innerClassName = classArr[1];
                                    Class pCls = Parse.parse(publicClass.trim(), cls.getGenConfig());
                                    if(pCls !=null && !CollectionUtils.isEmpty(pCls.getInnerClass())){
                                        for(Class innerCls:pCls.getInnerClass()){
                                            if(innerClassName.equals(innerCls.getName())){
                                                paramClass = innerCls;
                                            }
                                        }
                                    }
                                }
                            }else{
                                paramClass = Parse.parse(paramType.trim(), cls.getGenConfig());
                            }

                            if(paramClass != null){
                                docPojoClassList.add(new DocPojoClass(paramClass));
                            }
                        }
                        docField.setTypeDoc(docPojoClassList);
                    }
                }

                if (CollectionUtils.isEmpty(docField.getTypeDoc())) {
                    //尝试字段解析类型
                    Class paramClass = Parse.autoParse(field);
                    if(paramClass != null){
                        docField.setTypeDoc(Lists.newArrayList(new DocPojoClass(paramClass)));
                    }
                }


                this.fields.add(docField);
            }
        }

    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public List<DocField> getFields() {
        return fields;
    }

    public void setFields(List<DocField> fields) {
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }
}
