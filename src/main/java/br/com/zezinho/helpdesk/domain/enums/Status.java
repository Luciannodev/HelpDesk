package br.com.zezinho.helpdesk.domain.enums;

import lombok.Getter;

@Getter
public enum Status {
    ABERTO(0, "ABERTO"),
    ANDAMENTO(1, "ANDAMENTO"),
    ENCERRADO(2, "ENCERRADO");

    private Integer codigo;
    private String descricao;

    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Status toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Status status : Status.values()) {
            if (status.codigo.equals(cod)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido");
    }
}
