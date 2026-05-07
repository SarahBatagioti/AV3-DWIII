package com.autobots.automanager.servicos.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class RemovedorClienteServico {

    @Autowired
    private ClienteRepositorio repositorio;

    public void removerPorId(Long id) {
        if (!repositorio.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente nao encontrado");
        }
        repositorio.deleteById(id);
    }
}
