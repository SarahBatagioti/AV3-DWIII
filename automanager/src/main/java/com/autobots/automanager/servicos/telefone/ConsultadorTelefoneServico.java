package com.autobots.automanager.servicos.telefone;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class ConsultadorTelefoneServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public List<Telefone> listarPorCliente(Long clienteId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));
        return cliente.getTelefones();
    }

    public Telefone buscarPorId(Long clienteId, Long telefoneId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        return cliente.getTelefones().stream()
            .filter(tel -> telefoneId.equals(tel.getId()))
            .findFirst()
            .orElseThrow(() -> new RecursoNaoEncontradoException("Telefone nao encontrado neste cliente"));
    }
}
