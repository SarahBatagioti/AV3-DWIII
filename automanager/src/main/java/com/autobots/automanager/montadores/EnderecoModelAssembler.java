package com.autobots.automanager.montadores;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.dto.endereco.EnderecoDTO;

@Component
public class EnderecoModelAssembler {

    public EntityModel<Endereco> toModel(Long clienteId, Endereco endereco) {
        return EntityModel.of(
                endereco,
                linkTo(methodOn(EnderecoControle.class).buscarPorCliente(clienteId)).withSelfRel(),
                linkTo(methodOn(ClienteControle.class).obterCliente(clienteId)).withRel("cliente"),
                linkTo(methodOn(EnderecoControle.class).atualizar(clienteId, new EnderecoDTO())).withRel("atualizar"),
                linkTo(methodOn(EnderecoControle.class).remover(clienteId)).withRel("remover"));
    }
}
