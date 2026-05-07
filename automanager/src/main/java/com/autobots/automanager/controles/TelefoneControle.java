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

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;
import com.autobots.automanager.montadores.TelefoneModelAssembler;
import com.autobots.automanager.servicos.telefone.AtualizadorTelefoneServico;
import com.autobots.automanager.servicos.telefone.CadastradorTelefoneServico;
import com.autobots.automanager.servicos.telefone.ConsultadorTelefoneServico;
import com.autobots.automanager.servicos.telefone.RemovedorTelefoneServico;

@RestController
@RequestMapping("/clientes/{clienteId}/telefones")
public class TelefoneControle {

    @Autowired
    private CadastradorTelefoneServico cadastrador;

    @Autowired
    private ConsultadorTelefoneServico consultador;

    @Autowired
    private AtualizadorTelefoneServico atualizador;

    @Autowired
    private RemovedorTelefoneServico removedor;

    @Autowired
    private TelefoneModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Telefone>>> listar(@PathVariable Long clienteId) {
        return ResponseEntity.ok(assembler.toCollectionModel(clienteId, consultador.listarPorCliente(clienteId)));
    }

    @GetMapping("/{telefoneId}")
    public ResponseEntity<EntityModel<Telefone>> buscarPorId(@PathVariable Long clienteId, @PathVariable Long telefoneId) {
        Telefone telefone = consultador.buscarPorId(clienteId, telefoneId);
        return ResponseEntity.ok(assembler.toModel(clienteId, telefone));
    }

    @PostMapping
    public ResponseEntity<EntityModel<Telefone>> cadastrar(@PathVariable Long clienteId, @Valid @RequestBody TelefoneDTO dto) {
        Telefone criado = cadastrador.cadastrar(clienteId, dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{telefoneId}")
                .buildAndExpand(criado.getId())
                .toUri();
        return ResponseEntity.created(location).body(assembler.toModel(clienteId, criado));
    }

    @PutMapping("/{telefoneId}")
    public ResponseEntity<EntityModel<Telefone>> atualizar(@PathVariable Long clienteId,
                                              @PathVariable Long telefoneId,
                                              @Valid @RequestBody TelefoneDTO dto) {
        Telefone atualizado = atualizador.atualizar(clienteId, telefoneId, dto);
        return ResponseEntity.ok(assembler.toModel(clienteId, atualizado));
    }

    @DeleteMapping("/{telefoneId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId, @PathVariable Long telefoneId) {
        removedor.removerPorId(clienteId, telefoneId);
        return ResponseEntity.noContent().build();
    }
}
