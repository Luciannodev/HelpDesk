package br.com.zezinho.helpdesk.resources.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError  extends StandardError{
    private List<FieldMessages> erros = new ArrayList<>();

    public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addError(String fieldname,String message) {
        this.erros.add(new FieldMessages(fieldname,message));
    }
}
