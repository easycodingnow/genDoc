package com.easycodingnow.template.vo;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Field;
import com.easycodingnow.utils.CollectionUtils;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class DocPojoClass {
    private Class cls;

    @Getter
    private List<DocField> fields = new ArrayList<DocField>();

    @Getter
    private String type;

    @Getter
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
}
