package com.easycodingnow.template.vo;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Field;
import com.easycodingnow.utils.CollectionUtils;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class DocPojoClass {
    private Class cls;

    private List<DocField> fields = new ArrayList<DocField>();

    private String type;

    private String desc;


    public DocPojoClass(Class cls) {
        this.cls = cls;

        this.type = cls.getName();
        this.desc = cls.getCommentName();

        List<Field> fields = cls.getFields();

        if(!CollectionUtils.isEmpty(fields)){
            for(Field field:fields){
                DocField docField = new DocField();
                docField.setName(field.getName());
                docField.setDesc(field.getComment()!=null?field.getComment().getDescription():"");
                docField.setType(field.getType());
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
}
