package com.server.newhopeserver;

import java.util.HashMap;

public record badParam(String type, String title, HashMap<String, String> errores) {
    public badParam(HashMap<String, String> errores) {
        this("/", "Error en el mensaje recibido", errores);
    }
}
