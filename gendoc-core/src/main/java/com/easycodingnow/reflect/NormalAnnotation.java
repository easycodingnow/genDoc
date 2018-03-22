package com.easycodingnow.reflect;

;

import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/8
 */
public  class NormalAnnotation  extends Annotation{
    private List<Pair> pairList;


    public static class Pair{
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
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


    public List<Pair> getPairList() {
        return pairList;
    }

    public void setPairList(List<Pair> pairList) {
        this.pairList = pairList;
    }
}
