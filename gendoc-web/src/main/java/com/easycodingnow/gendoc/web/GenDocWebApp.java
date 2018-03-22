package com.easycodingnow.gendoc.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.stream.Collectors;

/**
 * @author lihao
 * @since 2018/3/21
 */


public class GenDocWebApp {
    private static final Log logger = LogFactory.getLog(GenDocWebApp.class);

    private GenWebConfig genConfig;


    public GenDocWebApp(GenWebConfig genConfig){
        this.genConfig = genConfig;
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
            logger.error("gen doc web start failed", e);
            return;
        }

        httpServer.createContext(genConfig.getContextPath(), new HttpHandler() {
            public void handle(HttpExchange httpExchange) throws IOException {
                String path = httpExchange.getRequestURI().getPath().replace(genConfig.getContextPath(), "");

                if(path !=null && path.length() > 0){
                    if(path.startsWith("/")){
                        path = path.substring(1);
                    }
                }

                if(path == null || path.isEmpty()){
                    path = genConfig.getDefaultFile();
                }


                String requestFilePath = genConfig.getFilePath()+path;

                try {
                    String result = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(requestFilePath)))
                            .lines().collect(Collectors.joining(System.lineSeparator()));
                    byte[] respContents = result.getBytes("UTF-8");
                    httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                    httpExchange.sendResponseHeaders(200, respContents.length);
                    httpExchange.getResponseBody().write(respContents);
                }catch (Exception e){
                    logger.error(e);
                }finally {
                    httpExchange.close();
                }
            }
        });

        httpServer.start();
        logger.info("gendoc web startup success! port:" + genConfig.getPort());
    }

    public static void main(String[] args){
         new GenDocWebApp(new GenWebConfig()).start();
    }

}
