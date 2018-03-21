package com.easycodingnow.gendoc.web.exception;

/**
 * @author lihao
 * @since 2018/3/21
 */
public class WebAppStartException extends RuntimeException {

    public WebAppStartException() {
        super();
    }


    public WebAppStartException(String s) {
        super(s);
    }


    public WebAppStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebAppStartException(Throwable cause) {
        super(cause);
    }
}
