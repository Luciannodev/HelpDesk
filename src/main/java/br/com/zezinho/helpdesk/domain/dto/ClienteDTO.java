package br.com.zezinho.helpdesk.domain.dto;

import br.com.zezinho.helpdesk.domain.Cliente;
import br.com.zezinho.helpdesk.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
public class ClienteDTO implements Serializable {

    private Integer id;
    @NotNull(message = "O campo nome é requerido")
    protected String nome;
    @NotNull(message = "O campo cpf é requerido")
    protected String cpf;
    @NotNull(message = "O campo email é requerido")
    protected String email;
    @NotNull(message = "O campo senha é requerido")
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(pattern = "dd/MM/YYYY")
    protected LocalDate datacriacao = LocalDate.now();

    public ClienteDTO() {
        super();
    }

    public ClienteDTO(Cliente cliente) {
        super();
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.email = cliente.getEmail();
        this.senha = cliente.getSenha();
        this.perfis = cliente.getPerfis().stream().map(x-> x.getCodigo()).collect(Collectors.toSet());
        this.datacriacao = cliente.getDatacriacao();

    }
    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x-> Perfil.toEnum(x)).collect(Collectors.toSet());
    }
    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
