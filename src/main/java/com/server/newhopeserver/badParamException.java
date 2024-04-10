package com.server.newhopeserver;

import java.util.HashMap;

public class badParamException extends RuntimeException {
    HashMap<String, String> badParam;

    public badParamException() {
        super();
    }

    public badParamException(HashMap<String, String> error) {
        badParam = error;
    }

}