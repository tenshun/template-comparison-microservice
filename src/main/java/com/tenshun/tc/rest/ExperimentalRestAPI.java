package com.tenshun.tc.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Robert S.
 */

@RestController(value = "/api/v2")
public class ExperimentalRestAPI {

    private final Logger logger = LoggerFactory.getLogger(ExperimentalRestAPI.class);

    @PostMapping(value = "/process")
    public @ResponseBody
    ResponseEntity<?> processText(@RequestBody String text) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String dummyObject = UUID.randomUUID().toString();
        return new ResponseEntity<>(dummyObject, httpHeaders, HttpStatus.OK);
    }
}
