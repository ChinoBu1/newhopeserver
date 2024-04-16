package com.server.newhopeserver;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Controller
public class protoController {
    @GetMapping(value = "/msg.proto", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile() throws IOException {
        InputStream ioStream = new ClassPathResource("/static/msg.proto").getInputStream();
        return ioStream.readAllBytes();

    }
}