package br.com.zezinho.helpdesk.repository;

import br.com.zezinho.helpdesk.domain.Pessoa;
import br.com.zezinho.helpdesk.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico,Integer> {

}
