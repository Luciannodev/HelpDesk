package br.com.zezinho.helpdesk.domain.enums;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
public enum Prioridade {
    BAIXA(0, "BAIXA"),
    MEDIA(1, "MÉDIA"),
    ALTA(2, "ALTA");

    private Integer codigo;
    private String descricao;

    Prioridade(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Prioridade toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Prioridade prioridade : Prioridade.values()) {
            if (prioridade.codigo.equals(cod)) {
                return prioridade;
            }
        }
        throw new IllegalArgumentException("prioridade inválida");
    }
}
