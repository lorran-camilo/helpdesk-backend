package com.lorran_camilo.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lorran_camilo.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
