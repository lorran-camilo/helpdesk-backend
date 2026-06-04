package com.lorran_camilo.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorran_camilo.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
