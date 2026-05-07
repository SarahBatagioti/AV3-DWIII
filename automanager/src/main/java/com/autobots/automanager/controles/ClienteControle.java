package com.autobots.automanager.controles;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.dto.cliente.ClienteAtualizacaoDTO;
import com.autobots.automanager.modelo.dto.cliente.ClienteCadastroDTO;
import com.autobots.automanager.montadores.ClienteModelAssembler;
import com.autobots.automanager.servicos.cliente.AtualizadorClienteServico;
import com.autobots.automanager.servicos.cliente.CadastradorClienteServico;
import com.autobots.automanager.servicos.cliente.ConsultadorClienteServico;
import com.autobots.automanager.servicos.cliente.RemovedorClienteServico;

@RestController
@RequestMapping("/clientes")
public class ClienteControle {

    @Autowired
    private CadastradorClienteServico cadastrador;

    @Autowired
    private ConsultadorClienteServico consultador;

    @Autowired
    private AtualizadorClienteServico atualizador;

    @Autowired
    private RemovedorClienteServico removedor;

    @Autowired
    private ClienteModelAssembler assembler;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> obterCliente(@PathVariable Long id) {
        Cliente cliente = consultador.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(cliente));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> obterClientes() {
        return ResponseEntity.ok(assembler.toCollectionModel(consultador.listar()));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> cadastrarCliente(@Valid @RequestBody ClienteCadastroDTO cliente) {
        Cliente clienteCriado = cadastrador.cadastrar(cliente);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clienteCriado.getId())
                .toUri();
        return ResponseEntity.created(location).body(assembler.toModel(clienteCriado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteAtualizacaoDTO atualizacao) {
        atualizacao.setId(id);
        Cliente clienteAtualizado = atualizador.atualizar(atualizacao);
        return ResponseEntity.ok(assembler.toModel(clienteAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        removedor.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}
