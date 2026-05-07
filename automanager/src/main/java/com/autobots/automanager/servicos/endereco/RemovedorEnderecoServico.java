package com.autobots.automanager.servicos.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class RemovedorEnderecoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public void remover(Long clienteId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        if (cliente.getEndereco() == null) {
            throw new RecursoNaoEncontradoException("Endereco nao encontrado para este cliente");
        }

        cliente.setEndereco(null);
        clienteRepositorio.save(cliente);
    }
}
