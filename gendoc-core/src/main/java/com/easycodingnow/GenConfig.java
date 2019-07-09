package com.easycodingnow;

import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.FileUtils;
import com.easycodingnow.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文档生成配置
 * @author lihao
 * @since 2018/3/21
 */
public class GenConfig {
    private static final Log logger = LogFactory.getLog(GenConfig.class);

    private static GenConfig genConfig = new GenConfig();


    private List<String> sourcePathRoot;

    private List<String> ignoreApiAnnotationParam;

    private List<String> ignoreApiTypeParam;

    private List<String> apiScanAnnotation;

    private List<String> apiScanCommentTag;

    private List<String> apiScanPackage;

    private String outputPath;

    private WebType webType = WebType.SPRING_MVC;

    private OutPutType outPutType = OutPutType.HTML;


    public static GenConfig getGenConfig(){
        return genConfig;
    }


    //校验配置是否合法
    boolean validate(){
        if(CollectionUtils.isEmpty(sourcePathRoot)){
            logger.error("gendoc gen failure！ GenConfig.sourcePathRoot was required!");
            return false;
        }


        if(StringUtils.isEmpty(outputPath)){
            logger.error("gendoc gen failure！ GenConfig.outputPath was required!");
            return false;
        }

        return true;

    }

    //解析的web框架类型，目前只支持spring mvc
    public static enum WebType{
        SPRING_MVC,
        RPC_API;
    }

    //输出文档类型，目前只支持HTML
    public static enum OutPutType{
        HTML,
        RPC_HTML,
        MARKDOWN,
        WORD;

    }


    public List<String> getApiScanPackage() {
        return apiScanPackage;
    }

    public void setApiScanPackage(List<String> apiScanPackage) {
        this.apiScanPackage = apiScanPackage;
    }

    public List<String> getSourcePathRoot() {
        return sourcePathRoot;
    }

    public void setSourcePathRoot(List<String> sourcePathRoot) {
        this.sourcePathRoot = sourcePathRoot;
    }

    /**
     * 设置一个path 只能解析sourcePathRoot
     * @param path
     */
    public void setSourcePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException(path + " sourcePath 文件夹不存在!");
        }
        if (!file.isDirectory()) {
            throw new RuntimeException(path + " sourcePath 必须是个文件夹!");
        }
        if (this.sourcePathRoot == null) {
            this.sourcePathRoot = new ArrayList<>();
        }

        this.sourcePathRoot.addAll(FileUtils.getJavaSourcePath(path));

        if (CollectionUtils.isEmpty(this.sourcePathRoot)) {
            throw new RuntimeException(path + " 下没有可解析的java源文件目录！");
        }
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public WebType getWebType() {
        return webType;
    }

    public void setWebType(WebType webType) {
        this.webType = webType;
    }

    public OutPutType getOutPutType() {
        return outPutType;
    }

    public void setOutPutType(OutPutType outPutType) {
        this.outPutType = outPutType;
    }

    public List<String> getIgnoreApiAnnotationParam() {
        return ignoreApiAnnotationParam;
    }

    public void setIgnoreApiAnnotationParam(List<String> ignoreApiAnnotationParam) {
        this.ignoreApiAnnotationParam = ignoreApiAnnotationParam;
    }

    public List<String> getApiScanAnnotation() {
        return apiScanAnnotation;
    }

    public void setApiScanAnnotation(List<String> apiScanAnnotation) {
        this.apiScanAnnotation = apiScanAnnotation;
    }

    public List<String> getApiScanCommentTag() {
        return apiScanCommentTag;
    }

    public void setApiScanCommentTag(List<String> apiScanCommentTag) {
        this.apiScanCommentTag = apiScanCommentTag;
    }

    public List<String> getIgnoreApiTypeParam() {
        return ignoreApiTypeParam;
    }

    public void setIgnoreApiTypeParam(List<String> ignoreApiTypeParam) {
        this.ignoreApiTypeParam = ignoreApiTypeParam;
    }
}
