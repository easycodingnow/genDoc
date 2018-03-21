package com.easycodingnow.reflect;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/8
 */
@Data
public  class NormalAnnotation  extends Annotation{
    private List<Pair> pairList;


    @Data
    public static class Pair{
        private String name;
        private String value;
    }

    public Pair getPair(String name){

        for(Pair pair:pairList){

            if(pair.getName().equals(name)){
                return pair;
            }

        }

        return null;
    }

    public String getValue(String name){
        Pair pair = getPair(name);

        return pair != null?pair.getValue():null;
    }

}
