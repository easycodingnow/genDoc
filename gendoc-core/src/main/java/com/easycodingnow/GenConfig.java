package com.easycodingnow;

import com.easycodingnow.utils.CollectionUtils;
import com.easycodingnow.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * 文档生成配置
 * @author lihao
 * @since 2018/3/21
 */
public class GenConfig {
    private static final Log logger = LogFactory.getLog(GenConfig.class);


    private List<String> scanPackages;

    private List<String> sourcePathRoot;

    private String outputPath;

    private WebType webType = WebType.SPRING_MVC;

    private OutPutType outPutType = OutPutType.HTML;


    //校验配置是否合法
    boolean validate(){
        if(CollectionUtils.isEmpty(sourcePathRoot)){
            logger.error("gendoc gen failure！ GenConfig.sourcePathRoot was required!");
            return false;
        }

        if(CollectionUtils.isEmpty(scanPackages)){
            logger.error("gendoc gen failure！ GenConfig.scanPackages was required!");
            return false;
        }

        if(StringUtils.isEmpty(outputPath)){
            logger.error("gendoc gen failure！ GenConfig.outputPath was required!");
            return false;
        }

        return true;

    }

    //解析的web框架类型，目前只支持spring mvc
    enum WebType{
        SPRING_MVC;
    }

    //输出文档类型，目前只支持HTML
    enum OutPutType{

        HTML,
        MARKDOWN,
        WORD;

    }

    public List<String> getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

    public List<String> getSourcePathRoot() {
        return sourcePathRoot;
    }

    public void setSourcePathRoot(List<String> sourcePathRoot) {
        this.sourcePathRoot = sourcePathRoot;
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
}
