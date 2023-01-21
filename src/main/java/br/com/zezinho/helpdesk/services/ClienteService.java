package br.com.zezinho.helpdesk.services;

import br.com.zezinho.helpdesk.domain.Pessoa;
import br.com.zezinho.helpdesk.domain.Cliente;
import br.com.zezinho.helpdesk.domain.dto.ClienteDTO;
import br.com.zezinho.helpdesk.repository.ClienteRepository;
import br.com.zezinho.helpdesk.repository.PessoaRepository;
import br.com.zezinho.helpdesk.services.exceptions.DataIntegrityViolationException;
import br.com.zezinho.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("O Cliente de ID: " + id + " não existe"));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente create(ClienteDTO clienteDto) {
        validaPorCpfEEmail(clienteDto);
        clienteDto.setSenha(encoder.encode(clienteDto.getSenha()));
        Cliente cliente = new Cliente(clienteDto);
        return clienteRepository.save(cliente);

    }

    public Cliente update(Integer id, @Valid ClienteDTO clienteDto) {
        clienteDto.setId(id);
        Cliente cliente = findById(id);
        validaPorCpfEEmail(clienteDto);
        cliente = new Cliente(clienteDto);
        return clienteRepository.save(cliente);
    }

    private void validaPorCpfEEmail(ClienteDTO clienteDto) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(clienteDto.getCpf());
        if (pessoa.isPresent() && pessoa.get().getId() != clienteDto.getId()) {
            throw new DataIntegrityViolationException("Cpf já cadastrado no sistema! ");
        }
        pessoa = pessoaRepository.findByEmail(clienteDto.getEmail());
        if (pessoa.isPresent() && pessoa.get().getId() != clienteDto.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }

    public void delete(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isPresent() && cliente.get().getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado");
        }
        clienteRepository.delete(cliente.orElseThrow(() -> new ObjectNotFoundException("O Usuario que deseja deletar não existe")));

    }
}
