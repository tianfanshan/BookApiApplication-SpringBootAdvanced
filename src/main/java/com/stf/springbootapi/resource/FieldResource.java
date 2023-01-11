package com.stf.springbootapi.resource;

public class FieldResource {

    private String resource;
    private String field;
    private String code;
    private String message;

    public FieldResource(String resource, String filed, String code, String message) {
        this.resource = resource;
        this.field = filed;
        this.code = code;
        this.message = message;
    }

    public String getResource() {
        return resource;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
