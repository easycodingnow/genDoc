package com.easycodingnow.reflect;

import com.easycodingnow.utils.CollectionUtils;
import lombok.Data;

import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public class Comment {

    private String description;

    List<Tag> tags;

    public Comment.Tag getTagByTagName(String tagName){
        if(!CollectionUtils.isEmpty(tags)){
            for(Comment.Tag tag:tags){
                if(tag.getTagName().equals(tagName)){
                    return  tag;
                }
            }
        }
        return null;
    }

    public Comment.Tag getParamTagByName(String paramName){
        if(!CollectionUtils.isEmpty(tags)){
            for(Comment.Tag tag:tags){
                if(tag.getName().equals(paramName)){
                    return tag;
                }
            }
        }

        return null;
    }

    @Data
    public static class Tag{
        String tagName;

        String name;

        String content;
    }
}
