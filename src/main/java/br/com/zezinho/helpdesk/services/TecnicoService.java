package br.com.zezinho.helpdesk.services;

import br.com.zezinho.helpdesk.domain.Pessoa;
import br.com.zezinho.helpdesk.domain.Tecnico;
import br.com.zezinho.helpdesk.domain.dto.TecnicoDTO;
import br.com.zezinho.helpdesk.repository.PessoaRepository;
import br.com.zezinho.helpdesk.repository.TecnicoRepository;
import br.com.zezinho.helpdesk.services.exceptions.DataIntegrityViolationException;
import br.com.zezinho.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
        return tecnico.orElseThrow(() -> new ObjectNotFoundException("O Técnico de ID: " + id + " não existe"));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO tecnicoDTO) {
        validaPorCpfEEmail(tecnicoDTO);
        tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
        Tecnico tecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(tecnico);

    }


    public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(id);
        Tecnico tecnico = findById(id);
        if (!tecnicoDTO.getSenha().equals(tecnico.getSenha()))
            tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
        validaPorCpfEEmail(tecnicoDTO);
        tecnico = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(tecnico);
    }

    private void validaPorCpfEEmail(TecnicoDTO tecnicoDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
        if (pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
            throw new DataIntegrityViolationException("Cpf já cadastrado no sistema! ");
        }
        pessoa = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
        if (pessoa.isPresent() && pessoa.get().getId() != tecnicoDTO.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }

    public void delete(Integer id) {
        Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
        if (tecnico.isPresent() && tecnico.get().getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado");
        }
        tecnicoRepository.delete(tecnico.orElseThrow(() -> new ObjectNotFoundException("O Usuario que deseja deletar não existe")));

    }
}
