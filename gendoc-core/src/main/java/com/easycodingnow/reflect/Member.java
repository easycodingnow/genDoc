package com.easycodingnow.reflect;

import com.easycodingnow.GenConfig;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class Member {

    private GenConfig genConfig;

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


    public Member getParentMember() {
        return parentMember;
    }

    public void setParentMember(Member parentMember) {
        this.parentMember = parentMember;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public List<String> getModifier() {
        return modifier;
    }

    public void setModifier(List<String> modifier) {
        this.modifier = modifier;
    }

    public GenConfig getGenConfig() {
        if (parentMember != null) {
            return parentMember.getGenConfig();
        }
        return genConfig;
    }

    public void setGenConfig(GenConfig genConfig) {
        this.genConfig = genConfig;
    }
}
