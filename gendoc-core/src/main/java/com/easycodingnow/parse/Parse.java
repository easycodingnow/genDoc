package com.easycodingnow.parse;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.reflect.Field;
import com.easycodingnow.reflect.Method;
import com.easycodingnow.reflect.MethodParam;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihao on 2018/3/8.
 */
public class Parse {

    private static final Log logger = LogFactory.getLog(Parse.class);

    public static Class parse(String fullyName, String[] sourceRoot){

        for(String sr:sourceRoot){
            String absPath = sr+"/"+fullyName.replaceAll("\\.", "/")+".java";
            File file = new File(absPath);
            if(file.exists() && file.isFile()){
                try {
                    return parse(new FileInputStream(file), sourceRoot);
                } catch (FileNotFoundException e) {
                    //
                }
            }
        }

        logger.warn("class: "+fullyName+" not found in sourceRoot list!");

        return null;
    }

    public static Class parse(InputStream inputStream, String[] sourceRoot){
        CompilationUnit cu = JavaParser.parse(inputStream);
        return innerParse(cu, sourceRoot);
    }

    private static Class innerParse(CompilationUnit cu, final String[] sourceRoot){
        final Class cls = new Class();

        cu.accept(new VoidVisitorAdapter<Void>(){

            //解析包名
            public void visit(PackageDeclaration n, Void arg) {
                cls.setPackageName(n.getNameAsString());
            }

            //解析类
            public void visit(ClassOrInterfaceDeclaration n, Void arg) {
                cls.setName(n.getNameAsString());
                cls.setComment(ParseHelper.parseComment(n));
                cls.setAnnotations(ParseHelper.parseAnnotation(n, cls));
                cls.setModifier(ParseHelper.parseModifiers(n));
                cls.setSourceRoot(sourceRoot);
                super.visit(n, arg);
            }

            //解析字段
            public void visit(FieldDeclaration n,  Void arg) {
                NodeList<VariableDeclarator> nodeList =  n.getVariables();

                if(nodeList != null && nodeList.size() > 0){
                    Field field = new Field();
                    VariableDeclarator variableDeclarator = nodeList.get(0);
                    field.setName(variableDeclarator.getNameAsString());
                    field.setType(variableDeclarator.getType().asString());
                    field.setComment(ParseHelper.parseComment(n));
                    field.setAnnotations(ParseHelper.parseAnnotation(n, field));
                    field.setModifier(ParseHelper.parseModifiers(n));
                    field.setParentMember(cls);

                    if(!field.ignore()){
                        List<Field> fields = cls.getFields();
                        if(fields == null){
                            fields = new ArrayList<Field>();
                            cls.setFields(fields);
                        }
                        fields.add(field);
                    }

                }
                super.visit(n, arg);

            }

            //解析方法
            public void visit(MethodDeclaration n, Void arg) {
                Method method = new Method();

                method.setName(n.getNameAsString());
                method.setType(n.getTypeAsString());
                method.setComment(ParseHelper.parseComment(n));
                method.setAnnotations(ParseHelper.parseAnnotation(n, method));
                method.setModifier(ParseHelper.parseModifiers(n));
                method.setParentMember(cls);

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
                    List<Method> methods = cls.getMethods();
                    if(methods == null){
                        methods = new ArrayList<Method>();
                        cls.setMethods(methods);
                    }
                    methods.add(method);
                }
                super.visit(n, arg);
            }


        }, null);

        return cls;


    }
}
