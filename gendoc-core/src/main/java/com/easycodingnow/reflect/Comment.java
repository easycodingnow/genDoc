package com.easycodingnow.reflect;

import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class Comment {

    private String description;


    List<Tag> tags;


    public void setTags(List<Tag> tags) {
        this.tags = tags;
        parseMetaData();
    }


    /**
     * 解析注释的元数据元数据的格式为#name:value#
     */
    private void parseMetaData(){
        if(!CollectionUtils.isEmpty(tags)){
            for(Tag tag:tags){
                if(StringUtils.isNotEmpty(tag.getContent())){
                    String content = tag.getContent();
                    Map<String, String> md = tag.getMetaData();

                    boolean hasMatch = false;

                    StringBuilder sb = new StringBuilder("");
                    List<String> removeStrList = new ArrayList<String>();
                    for(int i=0; i<content.length(); i++){
                        char ch = content.charAt(i);
                        if(ch != '#'){
                            if(hasMatch){
                                sb.append(ch);
                            }else{
                                hasMatch = false;
                            }
                        }else{
                            if(hasMatch){
                                String[] metaArr = sb.toString().split(":", 2);
                                if(metaArr.length > 1){
                                    md.put(metaArr[0].trim(), metaArr[1].trim());
                                }
                                removeStrList.add("#"+sb.toString()+"#");
                                hasMatch = false;
                            }else{
                                hasMatch = true;
                            }
                        }

                    }

                    for(String removeStr:removeStrList){
                        content = content.replace(removeStr, "");
                    }

                    tag.setContent(content);
                }
            }

        }
    }

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
                if(tag.getName() !=null && tag.getName().equals(paramName)){
                    return tag;
                }
            }
        }

        return null;
    }

    public static class Tag{
        String tagName;

        String name;

        String content;

        Map<String, String> metaData = new HashMap<String, String>();

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Map<String, String> getMetaData() {
            return metaData;
        }

        public void setMetaData(Map<String, String> metaData) {
            this.metaData = metaData;
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }
}
