package br.com.zezinho.helpdesk.domain;

import br.com.zezinho.helpdesk.domain.dto.TecnicoDTO;
import br.com.zezinho.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
public class Tecnico extends Pessoa {
    @OneToMany(mappedBy = "tecnico")
    @JsonIgnore
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        addPerfil(Perfil.TECNICO);
    }
    public Tecnico(TecnicoDTO tecnico) {
        super();
        this.id = tecnico.getId();
        this.nome = tecnico.getNome();
        this.cpf = tecnico.getCpf();
        this.email = tecnico.getEmail();
        this.senha = tecnico.getSenha();
        this.perfis = tecnico.getPerfis().stream().map(x-> x.getCodigo()).collect(Collectors.toSet());
        this.datacriacao = tecnico.getDatacriacao();
        addPerfil(Perfil.TECNICO);

    }

    public Tecnico(String nome, String cpf, String email, String senha) {
        super(nome, cpf, email, senha);
        addPerfil(Perfil.TECNICO);
    }
}
