package com.lorran_camilo.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorran_camilo.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
