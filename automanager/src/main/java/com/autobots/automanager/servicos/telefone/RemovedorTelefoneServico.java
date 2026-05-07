package com.autobots.automanager.servicos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class RemovedorTelefoneServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public void removerPorId(Long clienteId, Long telefoneId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        Telefone telefoneARemover = cliente.getTelefones().stream()
            .filter(tel -> telefoneId.equals(tel.getId()))
            .findFirst()
            .orElseThrow(() -> new RecursoNaoEncontradoException("Telefone nao encontrado neste cliente"));

        cliente.getTelefones().remove(telefoneARemover);
        clienteRepositorio.save(cliente);
    }
}
