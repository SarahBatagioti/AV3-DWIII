package com.autobots.automanager.servicos.documento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class ConsultadorDocumentoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Documento buscarPorId(Long clienteId, Long documentoId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        return cliente.getDocumentos().stream()
            .filter(doc -> doc.getId().equals(documentoId))
            .findFirst()
            .orElseThrow(() -> new RecursoNaoEncontradoException("Documento nao encontrado neste cliente"));
    }

    public List<Documento> listarPorCliente(Long clienteId) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));
        return cliente.getDocumentos();
    }
}
