package br.com.zezinho.helpdesk.resources.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class FieldMessages implements Serializable {
    private String fieldName;
    private String message;

    public FieldMessages(String fieldname, String message) {
        this.fieldName = fieldname;
        this.message = message;
    }
}
