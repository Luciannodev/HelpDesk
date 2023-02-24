package br.com.zezinho.helpdesk.resources;

import br.com.zezinho.helpdesk.domain.Tecnico;
import br.com.zezinho.helpdesk.domain.dto.TecnicoDTO;
import br.com.zezinho.helpdesk.services.TecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
    @Autowired
    private TecnicoService service;
    @PreAuthorize("hasRole('TECNICO')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico tecnico = service.findById(id);
        return ResponseEntity.ok(new TecnicoDTO(tecnico));
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> listaTecnicos = service.findAll();
        List<TecnicoDTO> listaTecnicosDTO = listaTecnicos.stream().map(tecnico -> new TecnicoDTO(tecnico)).collect(Collectors.toList());
        return ResponseEntity.ok(listaTecnicosDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO tecnicoDTO) {
        Tecnico tecnico = service.create(tecnicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tecnico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PreAuthorize("hasRole('TECNICO')")
    @PutMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO tecnicoDTO) {
        Tecnico tecnico = service.update(id, tecnicoDTO);
        return ResponseEntity.ok().body(new TecnicoDTO(tecnico));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
