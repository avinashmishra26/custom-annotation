package com.learn.custom.annotation.exceptions;

/**
 * Created by avinash on 2/21/20.
 */
public class JsonConvertException extends RuntimeException {

    public JsonConvertException(String msg){
        super(msg);
    }
}
