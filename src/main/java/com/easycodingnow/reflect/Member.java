package com.easycodingnow.reflect;

import com.easycodingnow.utils.CollectionUtils;
import lombok.Data;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public class Member {

    private Member parentMember;

    private String packageName;

    private String name;

    private String type;

    private Comment comment;

    private List<Annotation> annotations;

    private List<String> modifier;

    public String getPackageName() {

        return packageName!=null?packageName:(parentMember!=null?parentMember.getPackageName():null);
    }


    public boolean ignore(){
        if(comment != null && comment.getDescription() != null){
           return comment.getDescription().contains("#ignore#");
        }

        return false;
    }

    public boolean isPublic(){

        if(!CollectionUtils.isEmpty(modifier)){
            return modifier.contains("public");
        }

        return false;
    }

    public Annotation getAnnotationByName(String name){
        List<Annotation> annotations = getAnnotations();

        if(!CollectionUtils.isEmpty(annotations)){
            for(Annotation annotation:annotations){
                if(annotation.getName().equals(name)){
                    return annotation;
                }
            }
        }

        return null;
    }
}
