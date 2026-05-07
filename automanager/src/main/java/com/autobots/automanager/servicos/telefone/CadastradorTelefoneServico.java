package com.autobots.automanager.servicos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class CadastradorTelefoneServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Telefone cadastrar(Long clienteId, TelefoneDTO dto) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        Telefone telefone = new Telefone();
        telefone.setDdd(dto.getDdd());
        telefone.setNumero(dto.getNumero());

        cliente.getTelefones().add(telefone);
        Cliente clientePersistido = clienteRepositorio.saveAndFlush(cliente);

        return clientePersistido.getTelefones().get(clientePersistido.getTelefones().size() - 1);
    }
}
