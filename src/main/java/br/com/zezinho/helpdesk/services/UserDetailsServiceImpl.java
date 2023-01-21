package br.com.zezinho.helpdesk.services;

import br.com.zezinho.helpdesk.domain.Pessoa;
import br.com.zezinho.helpdesk.repository.PessoaRepository;
import br.com.zezinho.helpdesk.security.UserSS;
import br.com.zezinho.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Pessoa pessoa = pessoaRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("email of user not found!"));
        UserSS user = new UserSS(pessoa.getId(), pessoa.getEmail(), pessoa.getSenha(), pessoa.getPerfis());
        return user;
    }
}
