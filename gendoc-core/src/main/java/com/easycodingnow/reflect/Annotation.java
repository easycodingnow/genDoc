package com.easycodingnow.reflect;


/**
 * @author lihao
 * @since 2018/3/8
 */
public abstract class Annotation {

    private Member member;

    private String name;


    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
