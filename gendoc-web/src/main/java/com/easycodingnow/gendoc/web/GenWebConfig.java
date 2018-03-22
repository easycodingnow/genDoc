package com.easycodingnow.gendoc.web;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class GenWebConfig {
    public GenWebConfig(){

    }

    private Boolean  enabled = true;


    //文档路径，以当前项目的classPath为准
    private String  filePath = "/genDoc/out/html/";

    //默认请求的文件名称
    private String  defaultFile = "doc.html";

    //请求路径前缀
    private String  contextPath = "/genDoc";

    //启动端口
    private Integer port = 9086;


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDefaultFile() {
        return defaultFile;
    }

    public void setDefaultFile(String defaultFile) {
        this.defaultFile = defaultFile;
    }
}
