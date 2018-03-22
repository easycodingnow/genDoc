package com.easycodingnow;

import com.alibaba.fastjson.JSON;
import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.TemplateParse;
import com.easycodingnow.template.vo.DocApiApiClass;
import com.easycodingnow.template.vo.spring.SpringConvertHelper;
import com.easycodingnow.utils.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
    private static final Log logger = LogFactory.getLog(GenDoc.class);

    public static void gen(GenConfig genConfig){

        if(!genConfig.validate()){
            //配置校验
            return;
        }

        List<String> sourcePathRoot = genConfig.getSourcePathRoot();

        String outPutPath = genConfig.getOutputPath();

        List<String> packageList = genConfig.getScanPackages();

        List<DocApiApiClass> docClassList = new ArrayList<DocApiApiClass>();

        //扫描解析
        for(String path:sourcePathRoot){
            for(String pk:packageList){
                String absPath = path+"/"+pk.replaceAll("\\.", "/");
                List<String> fileList = IOUtils.getJavaFileList(absPath);
                try {
                    for(String filePath:fileList){
                        Class cls = Parse.parse(new FileInputStream(filePath), sourcePathRoot);

                        if(GenConfig.WebType.SPRING_MVC.equals(genConfig.getWebType())){
                            DocApiApiClass docClass = parseSpringMvcClass(cls);
                            if(docClass != null){
                                docClassList.add(docClass);
                            }
                        }


                    }
                } catch (FileNotFoundException e) {
                    logger.error("file not found!", e);
                }
            }
        }


        if(GenConfig.OutPutType.HTML.equals(genConfig.getOutPutType())){
            //生成html
            genHtml(docClassList, outPutPath);
        }
    }

    private static DocApiApiClass parseSpringMvcClass(Class cls){
        if(cls.getAnnotationByName("Controller") != null || cls.getAnnotationByName("RestController") != null){
            if(!cls.ignore()){
                return SpringConvertHelper.convertToSpringMvcClass(cls);
            }
        }
        return null;
    }

    private static void genHtml(List<DocApiApiClass> docClassList, String outPutPath){
        try {

            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData.put("jsonData", JSON.toJSONString(docClassList));
            mapData.put("classList", docClassList);

            String html = TemplateParse.parseTemplate("html/index.ftl", mapData);
            File fileDir = new File(outPutPath);
            File outFile = new File(outPutPath+"/doc.html");

            boolean resultFlag = true;
            if(!fileDir.exists()){
                resultFlag = fileDir.mkdirs();
            }

            if(!outFile.exists()){
                resultFlag = outFile.createNewFile();
            }

            if(!resultFlag){
                throw new Exception("create outFile `"+outFile+"` failure! Please ensure that the files exist and have read and write permissions.");
            }

            IOUtils.copy(html, new FileOutputStream(outFile));
        } catch (Exception e) {
            logger.error("parsing failure!", e);
        }
    }



}
