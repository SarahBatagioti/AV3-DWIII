package com.autobots.automanager.montadores;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;

@Component
public class TelefoneModelAssembler {

    public EntityModel<Telefone> toModel(Long clienteId, Telefone telefone) {
        return EntityModel.of(
                telefone,
                linkTo(methodOn(TelefoneControle.class).buscarPorId(clienteId, telefone.getId())).withSelfRel(),
                linkTo(methodOn(TelefoneControle.class).listar(clienteId)).withRel("telefones"),
                linkTo(methodOn(ClienteControle.class).obterCliente(clienteId)).withRel("cliente"),
                linkTo(methodOn(TelefoneControle.class).atualizar(clienteId, telefone.getId(), new TelefoneDTO())).withRel("atualizar"),
                linkTo(methodOn(TelefoneControle.class).remover(clienteId, telefone.getId())).withRel("remover"));
    }

    public CollectionModel<EntityModel<Telefone>> toCollectionModel(Long clienteId, List<Telefone> telefones) {
        List<EntityModel<Telefone>> modelos = telefones.stream()
                .map(telefone -> toModel(clienteId, telefone))
                .collect(Collectors.toList());

        return CollectionModel.of(
                modelos,
                linkTo(methodOn(TelefoneControle.class).listar(clienteId)).withSelfRel(),
                linkTo(methodOn(ClienteControle.class).obterCliente(clienteId)).withRel("cliente"),
                linkTo(methodOn(TelefoneControle.class).cadastrar(clienteId, new TelefoneDTO())).withRel("cadastrar"));
    }
}
