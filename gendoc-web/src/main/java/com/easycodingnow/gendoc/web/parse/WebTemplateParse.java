package com.easycodingnow.gendoc.web.parse;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class WebTemplateParse {
    private static WebTemplateParse templateParse = new WebTemplateParse();
    private   Configuration configuration;

    private WebTemplateParse(){
        configuration = new Configuration(Configuration.getVersion());
        configuration.setTemplateLoader(new ClassTemplateLoader(WebTemplateParse.class, "/gendoc/template/html"));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.getVersion()));
        configuration.setDefaultEncoding("UTF-8");
    }

    public static String parseTemplate(String templateKey, Map<String, Object> data) throws Exception{
        Template template = getInstance().configuration.getTemplate(templateKey);

        StringWriter stringWriter = new StringWriter();

        template.process(data, stringWriter);

        return stringWriter.toString();
    }

    private static WebTemplateParse getInstance(){
        return templateParse;
    }



}
