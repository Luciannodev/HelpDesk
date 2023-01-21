package br.com.zezinho.helpdesk.services;

import br.com.zezinho.helpdesk.domain.Chamado;
import br.com.zezinho.helpdesk.domain.Cliente;
import br.com.zezinho.helpdesk.domain.Tecnico;
import br.com.zezinho.helpdesk.domain.enums.Perfil;
import br.com.zezinho.helpdesk.domain.enums.Prioridade;
import br.com.zezinho.helpdesk.domain.enums.Status;
import br.com.zezinho.helpdesk.repository.ChamadoRepository;
import br.com.zezinho.helpdesk.repository.ClienteRepository;
import br.com.zezinho.helpdesk.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    public void instanciaDB(){
        Tecnico tec1 = new Tecnico
                ("jose luciano","13731581744","jose.dev@gamil.com", encoder.encode("123456"));
        tec1.addPerfil(Perfil.TECNICO);
        Cliente cli1 = new Cliente("Xavier da silva","74966278791","jose.luciano@gamilcom",encoder.encode("123456"));
        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO,"chamado 01","primerio chamado",tec1,cli1);

        tecnicoRepository.saveAll(Arrays.asList(tec1));
        clienteRepository.saveAll(Arrays.asList(cli1));
        chamadoRepository.saveAll(Arrays.asList(c1));

    }
}
