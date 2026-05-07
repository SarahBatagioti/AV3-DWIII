package com.autobots.automanager.servicos.documento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.modelo.dto.documento.DocumentoDTO;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class CadastradorDocumentoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Documento cadastrar(Long clienteId, DocumentoDTO dto){
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        Documento documento = new Documento();
        documento.setTipo(dto.getTipo());
        documento.setNumero(dto.getNumero());

        cliente.getDocumentos().add(documento);
        Cliente clientePersistido = clienteRepositorio.saveAndFlush(cliente);

        return clientePersistido.getDocumentos().get(clientePersistido.getDocumentos().size() - 1);
    }

}
