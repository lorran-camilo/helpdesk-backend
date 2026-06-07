package com.lorran_camilo.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lorran_camilo.helpdesk.domain.Chamado;
import com.lorran_camilo.helpdesk.domain.Cliente;
import com.lorran_camilo.helpdesk.domain.Tecnico;
import com.lorran_camilo.helpdesk.domain.enums.Perfil;
import com.lorran_camilo.helpdesk.domain.enums.Prioridade;
import com.lorran_camilo.helpdesk.domain.enums.Status;
import com.lorran_camilo.helpdesk.repositories.ChamadoRepository;
import com.lorran_camilo.helpdesk.repositories.ClienteRepository;
import com.lorran_camilo.helpdesk.repositories.TecnicoRepository;

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

        public void instanciaDB() {
                Tecnico tec1 = new Tecnico(null, "Lorran Camilo", "47423946826", "lorran@mail.com",
                                encoder.encode("123456"));
                tec1.addPerfis(Perfil.ADMIN);
                Tecnico tec2 = new Tecnico(null, "Leonardo Souza", "44005420680", "leonardo@mail.com",
                                encoder.encode("654321"));
                Tecnico tec3 = new Tecnico(null, "Carlos Santana", "84332419235", "carlos@mail.com",
                                encoder.encode("123456"));
                Tecnico tec4 = new Tecnico(null, "Pedro Silva", "37025904716", "pedro@mail.com",
                                encoder.encode("654321"));

                Cliente cli1 = new Cliente(null, "Linus Torvalds", "82704157154", "torvalds@mail.com",
                                encoder.encode("654321"));
                Cliente cli2 = new Cliente(null, "Bill Gates", "67730955229", "bill@mail.com",
                                encoder.encode("123456"));
                Cliente cli3 = new Cliente(null, "Steve Jobs", "23758985404", "steve@mail.com",
                                encoder.encode("654321"));
                Cliente cli4 = new Cliente(null, "Mark Zuckerberg", "28009415103", "mark@mail.com",
                                encoder.encode("123456"));

                Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado",
                                tec1,
                                cli1);
                Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ANDAMENTO, "Chamado 02", "Segundo chamado", tec2,
                                cli2);
                Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ANDAMENTO, "Chamado 03", "Terceiro chamado",
                                tec3,
                                cli3);
                Chamado c4 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 04", "Quarto chamado", tec4,
                                cli4);

                tecnicoRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4));
                clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli3, cli4));
                chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
        }
}
