package com.autobots.automanager.servicos.telefone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.modelo.atualizadores.telefone.TelefoneAtualizador;
import com.autobots.automanager.modelo.dto.telefone.TelefoneDTO;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class AtualizadorTelefoneServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    private final TelefoneAtualizador atualizador = new TelefoneAtualizador();

    public Telefone atualizar(Long clienteId, Long telefoneId, TelefoneDTO dto) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        Telefone telefoneExistente = cliente.getTelefones().stream()
            .filter(tel -> telefoneId.equals(tel.getId()))
            .findFirst()
            .orElseThrow(() -> new RecursoNaoEncontradoException("Telefone nao encontrado neste cliente"));

        Telefone novosDados = new Telefone();
        novosDados.setId(telefoneId);
        novosDados.setDdd(dto.getDdd());
        novosDados.setNumero(dto.getNumero());

        atualizador.atualizar(telefoneExistente, novosDados);
        clienteRepositorio.save(cliente);

        return telefoneExistente;
    }
}
