package com.commonfunction;

/**
 * Created by wjh on 2016/3/12.
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = -1708015121235851228L;

    public ServiceException(String message) {
        super(message);
    }
}