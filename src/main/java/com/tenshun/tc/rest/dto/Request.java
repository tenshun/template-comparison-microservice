package com.tenshun.tc.rest.dto;

import javax.validation.constraints.Size;

/**
 * @author Robert S.
 */

public class Request {

    private String guid;

    @Size(max = 256)
    private String text;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @Override
    public String toString() {
        return "Request{" +
                "guid='" + guid + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
