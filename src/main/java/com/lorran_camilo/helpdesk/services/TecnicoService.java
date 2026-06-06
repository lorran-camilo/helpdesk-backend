package com.lorran_camilo.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lorran_camilo.helpdesk.domain.Pessoa;
import com.lorran_camilo.helpdesk.domain.Tecnico;
import com.lorran_camilo.helpdesk.domain.dtos.TecnicoDTO;
import com.lorran_camilo.helpdesk.repositories.PessoaRepository;
import com.lorran_camilo.helpdesk.repositories.TecnicoRepository;
import com.lorran_camilo.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.lorran_camilo.helpdesk.services.exceptions.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado: " + id));
    }

    public List<Tecnico> findAll() {
        return repository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return repository.save(newObj);
    }

    public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico obj = findById(id);
        validaPorCpfEEmail(objDTO);
        obj = new Tecnico(objDTO);
        return repository.save(obj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getChamados().size() > 0) {
            throw new DataIntegrityViolationException("Tecnico não pode ser deletado pois possui chamados abertos.");
        }
        repository.deleteById(id);
    }

    private void validaPorCpfEEmail(TecnicoDTO objDTO) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema.");
        }
        obj = pessoaRepository.findByEmail(objDTO.getEmail());
        if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema.");
        }
    }
}
