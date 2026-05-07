package com.autobots.automanager.servicos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.modelo.dto.documento.DocumentoDTO;
import com.autobots.automanager.modelo.atualizadores.documento.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class AtualizadorDocumentoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    private DocumentoAtualizador atualizador = new DocumentoAtualizador();

    public Documento atualizar(Long clienteId, Long documentoId, DocumentoDTO dto) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        Documento documentoExistente = cliente.getDocumentos().stream()
            .filter(doc -> doc.getId().equals(documentoId))
            .findFirst()
            .orElseThrow(() -> new RecursoNaoEncontradoException("Documento nao encontrado neste cliente"));

        Documento novosDados = new Documento();
        novosDados.setTipo(dto.getTipo());
        novosDados.setNumero(dto.getNumero());

        atualizador.atualizar(documentoExistente, novosDados);
        clienteRepositorio.save(cliente);

        return documentoExistente;
    }
}
