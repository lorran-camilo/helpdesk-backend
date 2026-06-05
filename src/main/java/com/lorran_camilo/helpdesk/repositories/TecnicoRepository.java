package com.lorran_camilo.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorran_camilo.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
