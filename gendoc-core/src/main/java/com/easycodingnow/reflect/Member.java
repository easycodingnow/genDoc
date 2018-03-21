package com.easycodingnow.reflect;

import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;
import lombok.Data;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public class Member {

    private Member parentMember;

    private String packageName;

    private String[] sourceRoot;


    private String name;

    private String type;

    private Comment comment;

    private List<Annotation> annotations;

    private List<String> modifier;

    public String[] getSourceRoot() {

        return sourceRoot!=null?sourceRoot:(parentMember!=null?parentMember.getSourceRoot():null);
    }

    public String getPackageName() {

        return packageName!=null?packageName:(parentMember!=null?parentMember.getPackageName():null);
    }

    public String getCommentName(){
        String apiName = "";
        Comment comment = getComment();
        if(comment != null && StringUtils.isNotEmpty(comment.getDescription())){
            apiName = comment.getDescription().split("\n", 2)[0];
        }

//        if(StringUtils.isEmpty(apiName)){
//            apiName = getName();
//        }

        return apiName;
    }

    public String getCommentDesc(){
        Comment comment = getComment();
        if(comment != null && StringUtils.isNotEmpty(comment.getDescription())){
            String[] strArr = comment.getDescription().split("\n", 2);
            return strArr.length > 1?strArr[1].trim():"";
        }

        return "";
    }


    public boolean ignore(){
        if(getAnnotationByName("Deprecated") != null){
            return true;
        }

        if(comment != null && comment.getDescription() != null){
           return comment.getDescription().contains("#ignore#");
        }

        return false;
    }

    public boolean isPublic() {
        return !CollectionUtils.isEmpty(modifier) && modifier.contains("public");

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
