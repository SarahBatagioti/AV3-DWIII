package com.autobots.automanager.servicos.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class ConsultadorClienteServico {

    @Autowired
    private ClienteRepositorio repositorio;

    public Cliente buscarPorId(Long id) {
        return repositorio.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));
    }

    public List<Cliente> listar() {
        return repositorio.findAll();
    }
}
