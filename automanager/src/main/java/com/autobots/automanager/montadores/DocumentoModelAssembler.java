package com.autobots.automanager.montadores;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.dto.documento.DocumentoDTO;

@Component
public class DocumentoModelAssembler {

    public EntityModel<Documento> toModel(Long clienteId, Documento documento) {
        return EntityModel.of(
                documento,
                linkTo(methodOn(DocumentoControle.class).buscarPorId(clienteId, documento.getId())).withSelfRel(),
                linkTo(methodOn(DocumentoControle.class).listar(clienteId)).withRel("documentos"),
                linkTo(methodOn(ClienteControle.class).obterCliente(clienteId)).withRel("cliente"),
                linkTo(methodOn(DocumentoControle.class).atualizar(clienteId, documento.getId(), new DocumentoDTO())).withRel("atualizar"),
                linkTo(methodOn(DocumentoControle.class).remover(clienteId, documento.getId())).withRel("remover"));
    }

    public CollectionModel<EntityModel<Documento>> toCollectionModel(Long clienteId, List<Documento> documentos) {
        List<EntityModel<Documento>> modelos = documentos.stream()
                .map(documento -> toModel(clienteId, documento))
                .collect(Collectors.toList());

        return CollectionModel.of(
                modelos,
                linkTo(methodOn(DocumentoControle.class).listar(clienteId)).withSelfRel(),
                linkTo(methodOn(ClienteControle.class).obterCliente(clienteId)).withRel("cliente"),
                linkTo(methodOn(DocumentoControle.class).cadastrar(clienteId, new DocumentoDTO())).withRel("cadastrar"));
    }
}
