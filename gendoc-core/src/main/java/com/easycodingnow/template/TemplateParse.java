package com.easycodingnow.template;

import com.easycodingnow.reflect.Class;
import com.easycodingnow.utils.IOUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihao
 * @since 2018/3/8
 */
public class TemplateParse {
    private static TemplateParse templateParse = new TemplateParse();
    private   Configuration configuration;
    private List<String> loadTemplateList = new ArrayList<String>();


    private Template getTemplate(String key) throws Exception{
        if(!loadTemplateList.contains(key)){
            String content;
            content = loadTemplateFromConfig(key);

            if(content == null){
                content = loadTemplateFromDisk(key);
            }

            if(content == null){
               throw  new Exception("template `"+key+"` not exist!");
            }

            StringTemplateLoader stringTemplateLoader = (StringTemplateLoader) configuration.getTemplateLoader();
            stringTemplateLoader.putTemplate(key, content);
            loadTemplateList.add(key);
        }

        return configuration.getTemplate(key);
    }

    private String loadTemplateFromDisk(String key){
        String file = getClass().getResource("/").getFile()+ "gendoc/template/" +key;

        try {
           return IOUtils.toString(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String loadTemplateFromConfig(String key){
        return null;
    }

    private TemplateParse(){
        configuration = new Configuration(Configuration.getVersion());
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(stringTemplateLoader);
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.getVersion()));
        configuration.setDefaultEncoding("UTF-8");
    }

    public static String parseTemplate(String templateKey, Class cls) throws Exception{
        Template template = getInstance().getTemplate(templateKey);

        StringWriter stringWriter = new StringWriter();

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("class", cls);

        template.process(dataMap, stringWriter);

        return stringWriter.toString();
    }

    public static String parseTemplate(String templateKey, Map<String, Object> data) throws Exception{
        Template template = getInstance().getTemplate(templateKey);

        StringWriter stringWriter = new StringWriter();

        template.process(data, stringWriter);

        return stringWriter.toString();
    }

    private static TemplateParse getInstance(){
        return templateParse;
    }



}
