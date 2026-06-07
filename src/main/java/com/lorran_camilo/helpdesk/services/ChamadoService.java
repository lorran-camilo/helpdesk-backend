package com.lorran_camilo.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorran_camilo.helpdesk.domain.Chamado;
import com.lorran_camilo.helpdesk.domain.Cliente;
import com.lorran_camilo.helpdesk.domain.Tecnico;
import com.lorran_camilo.helpdesk.domain.dtos.ChamadoDTO;
import com.lorran_camilo.helpdesk.domain.enums.Prioridade;
import com.lorran_camilo.helpdesk.domain.enums.Status;
import com.lorran_camilo.helpdesk.repositories.ChamadoRepository;
import com.lorran_camilo.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.lorran_camilo.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado: " + id));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(ChamadoDTO objDTO) {
        return repository.save(newChamado(objDTO));
    }

    private Chamado newChamado(ChamadoDTO objDTO) {
        Tecnico tecnico = tecnicoService.findById(objDTO.getIdTecnico());
        Cliente cliente = clienteService.findById(objDTO.getIdCliente());

        Chamado chamado = new Chamado();
        if (objDTO.getId() != null) {
            chamado.setId(objDTO.getId());
        }

        if (objDTO.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(objDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(objDTO.getStatus()));
        chamado.setTitulo(objDTO.getTitulo());
        chamado.setObservacoes(objDTO.getObservacoes());

        return chamado;
    }

    public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado obj = findById(id);
        if (obj.getStatus() == Status.ENCERRADO) {
            throw new DataIntegrityViolationException("Chamado encerrado não pode ser atualizado.");
        }
        return repository.save(newChamado(objDTO));
    }
}
