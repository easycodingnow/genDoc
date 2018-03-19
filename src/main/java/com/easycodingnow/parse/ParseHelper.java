package com.easycodingnow.parse;

import com.easycodingnow.reflect.*;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.vo.SpringMvcMethod;
import com.easycodingnow.utils.CollectionUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithModifiers;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.JavadocBlockTag;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
class ParseHelper {

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

    static List<Annotation> parseAnnotation(NodeWithAnnotations<? extends Node> nodeWithAnnotations, Member member) {
        NodeList<AnnotationExpr> annotationExprs = nodeWithAnnotations.getAnnotations();

        if (annotationExprs != null && annotationExprs.size() > 0) {
            List<Annotation> annotations = new ArrayList<Annotation>();

            for (AnnotationExpr annotationExpr : annotationExprs) {
                Annotation annotation = null;

                if (MarkerAnnotation.class.isInstance(annotationExpr)) {
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

