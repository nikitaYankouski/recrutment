package com.hudman.codingtask.csv.exception;

import com.hudman.codingtask.exception.BaseException;

import java.io.Serial;

public class NotFound extends BaseException {

    @Serial
    private static final long serialVersionUID = 8777415230393628334L;

    public NotFound(String msg) {
        super(msg);
    }
}
