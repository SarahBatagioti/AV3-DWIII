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
import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.dto.cliente.ClienteAtualizacaoDTO;
import com.autobots.automanager.modelo.dto.cliente.ClienteCadastroDTO;

@Component
public class ClienteModelAssembler {

    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(
                cliente,
                linkTo(methodOn(ClienteControle.class).obterCliente(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteControle.class).obterClientes()).withRel("clientes"),
                linkTo(methodOn(DocumentoControle.class).listar(cliente.getId())).withRel("documentos"),
                linkTo(methodOn(TelefoneControle.class).listar(cliente.getId())).withRel("telefones"),
                linkTo(methodOn(EnderecoControle.class).buscarPorCliente(cliente.getId())).withRel("endereco"),
                linkTo(methodOn(ClienteControle.class).atualizarCliente(cliente.getId(), new ClienteAtualizacaoDTO())).withRel("atualizar"),
                linkTo(methodOn(ClienteControle.class).excluirCliente(cliente.getId())).withRel("remover"));
    }

    public CollectionModel<EntityModel<Cliente>> toCollectionModel(List<Cliente> clientes) {
        List<EntityModel<Cliente>> modelos = clientes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(
                modelos,
                linkTo(methodOn(ClienteControle.class).obterClientes()).withSelfRel(),
                linkTo(methodOn(ClienteControle.class).cadastrarCliente(new ClienteCadastroDTO())).withRel("cadastrar"));
    }
}
