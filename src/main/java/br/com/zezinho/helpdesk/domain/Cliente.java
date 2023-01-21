package br.com.zezinho.helpdesk.domain;

import br.com.zezinho.helpdesk.domain.dto.ClienteDTO;
import br.com.zezinho.helpdesk.domain.dto.TecnicoDTO;
import br.com.zezinho.helpdesk.domain.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class Cliente extends Pessoa{
    @OneToMany(mappedBy = "cliente")
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente() {
        super();
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente( String nome, String cpf, String email, String senha) {
        super( nome, cpf, email, senha);
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(ClienteDTO cliente) {
        super();
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.senha = cliente.getSenha();
        this.perfis = cliente.getPerfis().stream().map(x-> x.getCodigo()).collect(Collectors.toSet());
        this.datacriacao = cliente.getDatacriacao();
        addPerfil(Perfil.TECNICO);

    }
}
