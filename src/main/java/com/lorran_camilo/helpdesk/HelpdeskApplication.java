package com.lorran_camilo.helpdesk;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lorran_camilo.helpdesk.domain.Chamado;
import com.lorran_camilo.helpdesk.domain.Cliente;
import com.lorran_camilo.helpdesk.domain.Tecnico;
import com.lorran_camilo.helpdesk.domain.enums.Perfil;
import com.lorran_camilo.helpdesk.domain.enums.Prioridade;
import com.lorran_camilo.helpdesk.domain.enums.Status;
import com.lorran_camilo.helpdesk.repositories.ChamadoRepository;
import com.lorran_camilo.helpdesk.repositories.ClienteRepository;
import com.lorran_camilo.helpdesk.repositories.TecnicoRepository;

@SpringBootApplication
public class HelpdeskApplication implements CommandLineRunner {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelpdeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tecnico tec1 = new Tecnico(null, "Lorran Camilo", "12345678901", "lorran@mail.com", "123456");
		tec1.addPerfis(Perfil.ADMIN);

		Cliente cli1 = new Cliente(null, "Linus Torvalds", "98765432101", "torvalds@mail.com", "654321");

		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1,
				cli1);

		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
	}

}
