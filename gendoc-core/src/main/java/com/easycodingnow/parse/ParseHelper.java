package com.easycodingnow.parse;

import com.easycodingnow.reflect.*;
import com.easycodingnow.utils.CollectionUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithModifiers;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class ParseHelper {

    static private List<String> JAVA_LANG_TYPE = Lists.newArrayList(
                "String",
                "Integer",
                "int",
                "Long",
                "long",
                "Double",
                "double",
                "Float",
                "float",
                "Character",
                "char",
                "Short",
                "short",
                "Boolean",
                "boolean",
                "Byte",
                "byte",
                "Object"
    );


    static boolean isJavaLangType(String type){
        return JAVA_LANG_TYPE.contains(type);
    }

    static Comment parseComment(Node node) {
        if (node.getComment().isPresent()) {
            Javadoc javadoc = JavaParser.parseJavadoc(node.getComment().get().getContent());
            Comment comment = new Comment();
            comment.setDescription(javadoc.getDescription().toText());

            List<JavadocBlockTag> javadocBlockTags = javadoc.getBlockTags();

            if (javadocBlockTags != null && javadocBlockTags.size() > 0) {
                List<Comment.Tag> tags = new ArrayList<Comment.Tag>();

                for (JavadocBlockTag javadocBlockTag : javadocBlockTags) {
                    Comment.Tag tag = new Comment.Tag();
                    tag.setTagName(javadocBlockTag.getTagName());
                    tag.setName(javadocBlockTag.getName().orElse(null));
                    tag.setContent(javadocBlockTag.getContent().toText());
                    tags.add(tag);
                }

                comment.setTags(tags);
            }

            return comment;
        }

        return null;
    }

    static private String annotationValueTrim(String val){
        if(val.startsWith("\"") && val.endsWith("\"")){
           return val.substring(1, val.length() - 1);
        }

        return val;
    }


    /**
     * 寻找泛型参数
     * @param val
     * @return
     */
    static public String findGenericType(String val){
        String reg = "<(.+)>";
        Pattern r = Pattern.compile(reg);
        Matcher m = r.matcher(val);
        String findType = val;
        while (m.find()) {
            String a =  m.group(0);
            String matchStr = m.group(0).substring(1, m.group(0).length() - 1);
            if (matchStr.contains("<")) {
                findType = findGenericType(matchStr);
            } else if (matchStr.contains(",")) {
                findType = findGenericType(matchStr.split(",")[1].trim());
            } else {
                findType = matchStr;
            }

        }
        return findType;
    }

    public static void main(String[] args) {
        System.out.println(findGenericType("Result<Map<Long, SimpleGoodsInfo>>"));
        System.out.println(findGenericType("List<String>"));
        System.out.println(findGenericType("List<List<String>>"));
        System.out.println(findGenericType("List<List<String>>"));
        System.out.println(findGenericType("Map<Sting,Sting>"));
        System.out.println(findGenericType("Map<String,List<ds>>"));
    }

    static List<Annotation> parseAnnotation(NodeWithAnnotations<? extends Node> nodeWithAnnotations, Member member) {
        NodeList<AnnotationExpr> annotationExprs = nodeWithAnnotations.getAnnotations();

        if (annotationExprs != null && annotationExprs.size() > 0) {
            List<Annotation> annotations = new ArrayList<Annotation>();

            for (AnnotationExpr annotationExpr : annotationExprs) {
                Annotation annotation = null;

                if (MarkerAnnotationExpr.class.isInstance(annotationExpr)) {
                    annotation = new MarkerAnnotation();
                } else if (SingleMemberAnnotationExpr.class.isInstance(annotationExpr)) {
                    SingleMemberAnnotationExpr singleMemberAnnotationExpr = (SingleMemberAnnotationExpr) annotationExpr;

                    annotation = new SingleAnnotation();


                    SingleAnnotation singleAnnotation = (SingleAnnotation) annotation;
                    singleAnnotation.setValue(annotationValueTrim(singleMemberAnnotationExpr.getMemberValue().toString()));
                } else if (NormalAnnotationExpr.class.isInstance(annotationExpr)) {
                    NormalAnnotationExpr normalAnnotationExpr = (NormalAnnotationExpr) annotationExpr;

                    annotation = new NormalAnnotation();

                    NormalAnnotation normalAnnotation = (NormalAnnotation) annotation;

                    List<NormalAnnotation.Pair> pairList = new ArrayList<NormalAnnotation.Pair>();

                    for (MemberValuePair memberValuePair : normalAnnotationExpr.getPairs()) {
                        NormalAnnotation.Pair pair = new NormalAnnotation.Pair();
                        pair.setName(memberValuePair.getNameAsString());
                        pair.setValue(annotationValueTrim(memberValuePair.getValue().toString()));
                        pairList.add(pair);
                    }
                    normalAnnotation.setPairList(pairList);
                }

                if (annotation != null) {
                    annotation.setName(annotationExpr.getNameAsString());
                    annotation.setMember(member);
                    annotations.add(annotation);
                }
            }

            return annotations;
        }
        return null;
    }

    static List<String> parseModifiers(NodeWithModifiers<? extends Node> nodeWithModifiers) {

        EnumSet<Modifier> enumSet = nodeWithModifiers.getModifiers();

        if (enumSet != null && !enumSet.isEmpty()) {
            List<String> modifierList = new ArrayList<String>();
            for (Modifier modifier : enumSet) {
                modifierList.add(modifier.asString());
            }

            return modifierList;
        }

        return null;
    }
}

