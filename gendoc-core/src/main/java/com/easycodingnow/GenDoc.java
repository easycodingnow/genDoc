package com.easycodingnow;

import com.alibaba.fastjson.JSON;
import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.TemplateParse;
import com.easycodingnow.template.vo.spring.SpringConvertHelper;
import com.easycodingnow.template.vo.spring.SpringMvcClass;
import com.easycodingnow.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class GenDoc {


    public static void gen(GenConfig genConfig){
        String[] sourcePathRoot = genConfig.getSourcePathRoot();

        List<String> packageList = genConfig.getScanPackages();

        List<SpringMvcClass> springMvcClass = new ArrayList<SpringMvcClass>();

        for(String path:sourcePathRoot){

            for(String pk:packageList){
                String absPath = path+"/"+pk.replaceAll("\\.", "/");
                List<String> fileList = getJavaFileList(absPath);

                try {
                    for(String filePath:fileList){
                        Class cls = Parse.parse(new FileInputStream(filePath), sourcePathRoot);
                        if(cls.getAnnotationByName("Controller") != null || cls.getAnnotationByName("RestController") != null){
                            if(!cls.ignore()){
                                springMvcClass.add(SpringConvertHelper.convertToSpringMvcClass(cls));
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }



        Map<String, Object> classList = new HashMap<String, Object>();
        classList.put("jsonData", JSON.toJSONString(springMvcClass));
        classList.put("classList", springMvcClass);

        try {
            String html = TemplateParse.parseTemplate("html/index.ftl", classList);
            IOUtils.copy(html, new FileOutputStream("/Users/leeco/Desktop/text.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static List<String> getJavaFileList(String filePath) {
        List<String> fileList = new ArrayList<String>();
        File file = new File(filePath);
        if(file.exists()){
            File[] childFiles = file.listFiles();
            if(childFiles != null && childFiles.length > 0) {
                for (File childFile : childFiles) {
                    if (childFile.isDirectory()) {
                        fileList.addAll(getJavaFileList(childFile.getPath()));
                    } else {
                        String childFilePath = childFile.getPath();

                        if(childFilePath.endsWith(".java")){
                            fileList.add(childFilePath);
                        }
                    }
                }
            }
        }

        return fileList;
    }

}
