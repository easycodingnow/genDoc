package com.easycodingnow;

import com.alibaba.fastjson.JSON;
import com.easycodingnow.parse.Parse;
import com.easycodingnow.reflect.*;
import com.easycodingnow.reflect.Class;
import com.easycodingnow.template.TemplateParse;
import com.easycodingnow.template.vo.DocApiApiClass;
import com.easycodingnow.template.vo.rpc.RpcApiConvertHelper;
import com.easycodingnow.template.vo.spring.SpringConvertHelper;
import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.FileUtils;
import com.easycodingnow.utils.IOUtils;
import com.easycodingnow.utils.StringUtils;
import com.google.common.base.Stopwatch;
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
import java.util.concurrent.TimeUnit;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class GenDoc {
    private static final Log logger = LogFactory.getLog(GenDoc.class);

    private GenConfig genConfig;

    public static void gen(GenConfig genConfig){
        new GenDoc(genConfig).gen();
    }

    public static void gen(String sourcePath, String outPath){
        GenConfig genConfig = new GenConfig();
        genConfig.setOutputPath(outPath);
        genConfig.setSourcePath(sourcePath);
        gen(genConfig);
    }

    public static void gen(String sourcePath, String outPath, List<String> scanApipackage){
        GenConfig genConfig = new GenConfig();
        genConfig.setOutputPath(outPath);
        genConfig.setSourcePath(sourcePath);
        genConfig.setApiScanPackage(scanApipackage);
        gen(genConfig);
    }


    public GenDoc(GenConfig genConfig){
        this.genConfig = genConfig;
    }

    public void gen(){
        Stopwatch stopwatch  = Stopwatch.createStarted();
        logger.info("【genDoc】校验配置！");
        if(!genConfig.validate()){
            //配置校验
            return;
        }
        logger.info("【genDoc】配置校验完毕！");

        List<String> sourcePathRoot = genConfig.getSourcePathRoot();
        logger.info("【genDoc】扫描源文件配置 sourcePathRoot:" + sourcePathRoot);

        String outPutPath = genConfig.getOutputPath();
        logger.info("【genDoc】api文件输出路径配置 outPutPath:" + outPutPath);

        List<DocApiApiClass> docClassList = new ArrayList<DocApiApiClass>();

        logger.info("【genDoc】解析开始！");
        //扫描解析
        for(String path:sourcePathRoot){
            List<String> fileList = FileUtils.getJavaFileList(path);
            //并行解析
            fileList.parallelStream().forEach((filePath)->{
                Class cls;
                try {
                    cls = Parse.parse(new FileInputStream(filePath), genConfig);
                } catch (FileNotFoundException e) {
                    logger.error("file not found!", e);
                    return;
                }
                if(GenConfig.WebType.SPRING_MVC.equals(genConfig.getWebType())){
                    DocApiApiClass docClass = parseSpringMvcClass(cls);
                    if(docClass != null){
                        docClassList.add(docClass);
                    }
                } else if (GenConfig.WebType.RPC_API.equals(genConfig.getWebType())) {
                    DocApiApiClass docClass = parseRpcApiClass(cls);
                    if(docClass != null){
                        docClassList.add(docClass);
                    }
                }
            });
        }
        logger.info("【genDoc】 一共解析出" + docClassList.size() + "api文件，耗时:" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "毫秒");

        if(GenConfig.OutPutType.HTML.equals(genConfig.getOutPutType()) ||
                GenConfig.OutPutType.RPC_HTML.equals(genConfig.getOutPutType())){
            logger.info("【genDoc】 开始生成html文档");
            genHtml(docClassList, outPutPath);
        }
        logger.info("【genDoc】 解析完毕！共耗时:" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "毫秒");
    }

    private  DocApiApiClass parseSpringMvcClass(Class cls){
        boolean inPackage = false;

        if (!CollectionUtils.isEmpty(genConfig.getApiScanPackage())) {
            for (String packageName:genConfig.getApiScanPackage()){
                if (cls.getPackageName().startsWith(packageName)) {
                    inPackage = true;
                }
            }
        } else {
            inPackage = true;
        }

        if(inPackage && cls.getAnnotationByName("Controller") != null || cls.getAnnotationByName("RestController") != null){
            if(!cls.ignore()){
                return SpringConvertHelper.convertToSpringMvcClass(cls);
            }
        }
        return null;
    }

    private  DocApiApiClass parseRpcApiClass(Class cls){
        if(!cls.isInerface()){
            return null;
        }

        if(cls.ignore()){
            return null;
        }

        if (CollectionUtils.isEmpty(cls.getMethods())) {
            return null;
        }

        if (!CollectionUtils.isEmpty(genConfig.getApiScanPackage())) {
            for (String packageName:genConfig.getApiScanPackage()){
                if (cls.getPackageName().startsWith(packageName)) {
                    return RpcApiConvertHelper.convertToRpcApi(cls);
                }
            }
        } else {
            for (Method method:cls.getMethods()) {
                List<String> apiScanAnnotation = method.getGenConfig().getApiScanAnnotation();
                List<String> scanCommentTag = method.getGenConfig().getApiScanCommentTag();
                List<Annotation> annotations = method.getAnnotations();

                if (!CollectionUtils.isEmpty(apiScanAnnotation) && !CollectionUtils.isEmpty(annotations)) {
                    for (Annotation annotation:annotations) {
                        if (apiScanAnnotation.contains(annotation.getName())) {
                            return RpcApiConvertHelper.convertToRpcApi(cls);
                        }

                    }
                }

                if (!CollectionUtils.isEmpty(scanCommentTag) && method.getComment() != null
                        && !CollectionUtils.isEmpty(method.getComment().getTags())) {
                    for (Comment.Tag tag:method.getComment().getTags()) {
                        if (scanCommentTag.contains(tag.getTagName())) {
                            return RpcApiConvertHelper.convertToRpcApi(cls);
                        }
                    }
                }
            }
        }

        return null;
    }

    private  void genHtml(List<DocApiApiClass> docClassList, String outPutPath){
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData.put("jsonData", JSON.toJSONString(docClassList));
            mapData.put("classList", docClassList);
            String html = "";
            if (genConfig.getOutPutType().equals(GenConfig.OutPutType.HTML)) {
                html = TemplateParse.parseTemplate("html/index.ftl", mapData);
            } else if (genConfig.getOutPutType().equals(GenConfig.OutPutType.RPC_HTML)) {
                html = TemplateParse.parseTemplate("html/rpc.ftl", mapData);
            }

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
            logger.info("【genDoc】 html文档生成完成！ 耗时:"+stopwatch.elapsed(TimeUnit.MILLISECONDS)+"毫秒, 文档路径在:" + outFile);
        } catch (Exception e) {
            logger.error("parsing failure!", e);
        }
    }



}
