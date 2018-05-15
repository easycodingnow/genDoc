package com.easycodingnow.parse;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Field;
import com.easycodingnow.reflect.Method;
import com.easycodingnow.reflect.MethodParam;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class Parse {

    private static final Log logger = LogFactory.getLog(Parse.class);

    public static Class parse(String fullyName, List<String> sourceRoot){

        for(String sr:sourceRoot){
            String absPath = sr+"/"+fullyName.replaceAll("\\.", "/")+".java";
            File file = new File(absPath);
            if(file.exists() && file.isFile()){
                try {
                    return parse(new FileInputStream(file), sourceRoot);
                } catch (FileNotFoundException e) {
                    logger.error("file not found!", e);
                }
            }
        }

        logger.warn("class: "+fullyName+" not found in sourceRoot list!");

        return null;
    }

    public static Class parse(InputStream inputStream, List<String> sourceRoot){
        CompilationUnit cu = JavaParser.parse(inputStream);
        return innerParse(cu, sourceRoot);
    }

    private static Class innerParse(CompilationUnit cu, final List<String> sourceRoot){
        final Class cls = new Class();
        final HashMap<String , Class> clsMap = new HashMap(); //类名称和类的映射关系

        cu.accept(new VoidVisitorAdapter<Void>(){

            //解析包名
            public void visit(PackageDeclaration n, Void arg) {
                cls.setPackageName(n.getNameAsString());
            }

            //解析类
            public void visit(ClassOrInterfaceDeclaration n, Void arg) {

                if(n.getParentNode().isPresent()){
                    if(n.getParentNode().get() instanceof  ClassOrInterfaceDeclaration){
                        ClassOrInterfaceDeclaration parentNodeClass = (ClassOrInterfaceDeclaration) n.getParentNode().get();
                        //内部类
                        Class innerCls = new Class();
                        Class parentCls = clsMap.get(parentNodeClass.getNameAsString());
                        innerCls.setName(parentNodeClass.getNameAsString());
                        innerCls.setComment(ParseHelper.parseComment(parentNodeClass));
                        innerCls.setAnnotations(ParseHelper.parseAnnotation(parentNodeClass, innerCls));
                        innerCls.setModifier(ParseHelper.parseModifiers(parentNodeClass));
                        innerCls.setSourceRoot(sourceRoot);
                        innerCls.setPackageName(parentCls.getPackageName());

                        List<Class> innerClassList =  parentCls.getInnerClass();
                        if(innerClassList == null){
                            innerClassList = new ArrayList<>();
                            parentCls.setInnerClass(innerClassList);
                        }
                        innerClassList.add(innerCls);
                        clsMap.put(n.getNameAsString(), innerCls);
                    }else{
                        //最外层的cls
                        cls.setName(n.getNameAsString());
                        cls.setComment(ParseHelper.parseComment(n));
                        cls.setAnnotations(ParseHelper.parseAnnotation(n, cls));
                        cls.setModifier(ParseHelper.parseModifiers(n));
                        cls.setSourceRoot(sourceRoot);
                        clsMap.put(n.getNameAsString(), cls);
                    }

                }
                super.visit(n, arg);
            }

            //解析字段
            public void visit(FieldDeclaration n,  Void arg) {
                NodeList<VariableDeclarator> nodeList =  n.getVariables();

                if(nodeList != null && nodeList.size() > 0){
                    ClassOrInterfaceDeclaration parentCls = (ClassOrInterfaceDeclaration) n.getParentNode().get();
                    Class fieldClass = clsMap.get(parentCls.getNameAsString());


                    Field field = new Field();
                    VariableDeclarator variableDeclarator = nodeList.get(0);
                    field.setName(variableDeclarator.getNameAsString());
                    field.setType(variableDeclarator.getType().asString());
                    field.setComment(ParseHelper.parseComment(n));
                    field.setAnnotations(ParseHelper.parseAnnotation(n, field));
                    field.setModifier(ParseHelper.parseModifiers(n));
                    field.setParentMember(fieldClass);

                    if(!field.ignore()){
                        List<Field> fields = fieldClass.getFields();
                        if(fields == null){
                            fields = new ArrayList<Field>();
                            fieldClass.setFields(fields);
                        }
                        fields.add(field);
                    }

                }
                super.visit(n, arg);

            }

            //解析方法
            public void visit(MethodDeclaration n, Void arg) {
                if(n.getParentNode().isPresent() && n.getParentNode().get() instanceof ClassOrInterfaceDeclaration){
                    ClassOrInterfaceDeclaration parentCls = (ClassOrInterfaceDeclaration) n.getParentNode().get();
                    Class fieldClass = clsMap.get(parentCls.getNameAsString());

                    Method method = new Method();
                    method.setName(n.getNameAsString());
                    method.setType(n.getTypeAsString());
                    method.setComment(ParseHelper.parseComment(n));
                    method.setAnnotations(ParseHelper.parseAnnotation(n, method));
                    method.setModifier(ParseHelper.parseModifiers(n));
                    method.setParentMember(fieldClass);

                    NodeList<Parameter> nodeList = n.getParameters();
                    if(nodeList !=null && nodeList.size() > 0){
                        List<MethodParam> methodParamList = new ArrayList<MethodParam>();
                        for(Parameter parameter:nodeList){
                            MethodParam methodParam = new MethodParam();
                            methodParam.setType(parameter.getTypeAsString());
                            methodParam.setName(parameter.getNameAsString());
                            methodParam.setAnnotations(ParseHelper.parseAnnotation(parameter, methodParam));
                            methodParam.setParentMember(method);
                            methodParamList.add(methodParam);
                        }
                        method.setParams(methodParamList);
                    }

                    if(!method.ignore()){
                        List<Method> methods = fieldClass.getMethods();
                        if(methods == null){
                            methods = new ArrayList<Method>();
                            fieldClass.setMethods(methods);
                        }
                        methods.add(method);
                    }
                }
                super.visit(n, arg);
            }


        }, null);

        return cls;


    }
}
