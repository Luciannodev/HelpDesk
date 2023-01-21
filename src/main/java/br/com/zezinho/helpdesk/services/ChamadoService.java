package br.com.zezinho.helpdesk.services;

import br.com.zezinho.helpdesk.domain.Chamado;
import br.com.zezinho.helpdesk.domain.Cliente;
import br.com.zezinho.helpdesk.domain.Tecnico;
import br.com.zezinho.helpdesk.domain.dto.ChamadoDTO;
import br.com.zezinho.helpdesk.domain.enums.Prioridade;
import br.com.zezinho.helpdesk.domain.enums.Status;
import br.com.zezinho.helpdesk.repository.ChamadoRepository;
import br.com.zezinho.helpdesk.repository.ClienteRepository;
import br.com.zezinho.helpdesk.repository.TecnicoRepository;
import br.com.zezinho.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;


    public Chamado findById(Integer id) {
        Optional<Chamado> chamado = chamadoRepository.findById(id);
        return chamado.orElseThrow(() -> new ObjectNotFoundException("O Chamado de ID: " + id + " não existe"));
    }


    public List<Chamado> findAll() {
        List<Chamado> chamadoList = chamadoRepository.findAll();
        return chamadoList;
    }

    public Chamado create(@Valid ChamadoDTO chamadoDTO) {
        return newChamado(chamadoDTO);
    }

    public Chamado newChamado(ChamadoDTO chamadoDTO) {
        Optional<Tecnico> tecnico = tecnicoRepository.findById(chamadoDTO.getTecnico());
        Optional<Cliente> cliente = clienteRepository.findById(chamadoDTO.getCliente());
        Chamado chamado = new Chamado();
        if (chamadoDTO.getId() != null) {
            chamado.setId(chamadoDTO.getId());
        }
        if(chamadoDTO.getStatus()==2){
            chamado.setDataFechamento(LocalDate.now());
        }
        chamado.setTecnico(tecnico.orElseThrow(() -> new ObjectNotFoundException("O Técnico de id: " + chamadoDTO.getTecnico() + " não existe")));
        chamado.setCliente(cliente.orElseThrow(() -> new ObjectNotFoundException("O Cliente de id: " + chamadoDTO.getCliente() + " não existe")));
        chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setObservacoes(chamadoDTO.getObservacoes());
        return chamadoRepository.save(chamado);

    }


    public Chamado update(Integer id, ChamadoDTO chamadoDTO) {
        chamadoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("O Chamado selecionado não existe"));
        chamadoDTO.setId(id);
        return newChamado(chamadoDTO);
    }
}
