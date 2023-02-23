package br.com.zezinho.helpdesk.domain.enums;

import lombok.Getter;

@Getter
public enum Perfil {
    ADMIN(0, "ROLE_ADMIN"),
    CLIENTE(1, "ROLE_CLIENTE"),
    TECNICO(2, "ROLE_TECNICO");

    private Integer codigo;
    private String descricao;

    Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static Perfil toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }
        for (Perfil perfil : Perfil.values()) {
            if (perfil.codigo.equals(cod)) {
                return perfil;
            }
        }
        throw new IllegalArgumentException("Perfil inválido");
    }
}
