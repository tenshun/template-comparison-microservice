package com.tenshun.tc.rest;


import com.tenshun.tc.rest.dto.Request;
import com.tenshun.tc.service.TemplateMatchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Robert S.
 */

@RestController
@RequestMapping("/api/v1")
public class TemplateResource {

    private final Logger logger = LoggerFactory.getLogger(TemplateResource.class);

    @Autowired
    @Qualifier("fileBasedService")
    public TemplateMatchingService templateMatchingService;


    @PostMapping(path = "/process", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody ResponseEntity<?> compareWithTemplates(@RequestBody Request request) {
        logger.info("POST request to compare text with templates : {}", request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return templateMatchingService.compareWithTemplates(request.getText())
                .map(templates -> new ResponseEntity<>(templates, httpHeaders, HttpStatus.OK))
                .orElse(new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PostMapping(path = "/template/{guid}", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody ResponseEntity<?> compareWithTemplateByGuid(@PathVariable(value = "guid") String templateGuid,
                                                                     @RequestBody Request request) {
        logger.info("POST request to /v1/template/{guid}: {}" + templateGuid);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        if(templateMatchingService.compareWithTemplateByGuid(request.getText(), request.getGuid())){
            return new ResponseEntity<>("true", httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("false", httpHeaders, HttpStatus.OK); //todo refactor this
        }
    }

    @PostMapping(path = "/template", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody ResponseEntity<?> putNewTemplate(@RequestBody Request request) {
        logger.info("POST request to /v1/template: {}", request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return templateMatchingService.putToTemplateStorage(request.getText())
                .map(template -> new ResponseEntity<>(template, httpHeaders, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping(path = "/template/{guid}", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody ResponseEntity<?> getByGuid(@PathVariable(value = "guid") String templateGuid) {
        logger.info("GET request to /v1/template/{guid}: {}" + templateGuid);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return templateMatchingService.getPatternByGuid(templateGuid)
                .map(template -> new ResponseEntity<>(template, httpHeaders, HttpStatus.OK))
                .orElse(new ResponseEntity<>(httpHeaders, HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/templates", produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody ResponseEntity<?> getAllTemplates() {
        logger.info("GET request to /v1/templates");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return templateMatchingService.getAllTemplates()
                .map(templates -> new ResponseEntity<>(templates, httpHeaders, HttpStatus.OK))
                .orElse(new ResponseEntity<>(httpHeaders, HttpStatus.NOT_FOUND));
    }

}
