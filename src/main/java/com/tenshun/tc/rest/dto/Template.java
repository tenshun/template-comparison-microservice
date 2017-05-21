package com.tenshun.tc.rest.dto;

import javax.validation.constraints.Pattern;

public class Template {

    @Pattern(regexp = "[\\W\\w]*")
    private String template;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "Template{" +
                "template='" + template + '\'' +
                '}';
    }
}
