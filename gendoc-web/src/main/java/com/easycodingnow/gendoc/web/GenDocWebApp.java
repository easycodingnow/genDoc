package com.easycodingnow.gendoc.web;

import com.easycodingnow.gendoc.web.exception.WebAppStartException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author lihao
 * @since 2018/3/21
 */


public class GenDocWebApp {
    private static final Log logger = LogFactory.getLog(GenDocWebApp.class);

    private GenConfig genConfig;


    public GenDocWebApp(GenConfig genConfig){
        this.genConfig = genConfig;
        ContextHolder.getInstance().setGenConfig(genConfig);
    }


    private void initContext(){

    }


    public void start(){
        if(!genConfig.getEnabled()){
            logger.info("gendoc web disable！");
            return;
        }
        HttpServer httpServer = null;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(genConfig.getPort()), 0);
        } catch (IOException e) {
            throw new WebAppStartException(e);
        }

        httpServer.createContext("/genDoc/", new HttpHandler() {
            public void handle(HttpExchange httpExchange) throws IOException {


                byte[] respContents = "Hello World".getBytes("UTF-8");

                // 设置响应头
                httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                // 设置响应code和内容长度
                httpExchange.sendResponseHeaders(200, respContents.length);

                // 设置响应内容
                httpExchange.getResponseBody().write(respContents);

                // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
                httpExchange.close();
            }
        });

        httpServer.start();
        logger.info("gendoc web startup success! port:" + genConfig.getPort());
    }

    public static void main(String[] args){

    }

}
