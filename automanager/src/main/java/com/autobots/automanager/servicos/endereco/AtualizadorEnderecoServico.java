package com.autobots.automanager.servicos.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.modelo.atualizadores.endereco.EnderecoAtualizador;
import com.autobots.automanager.modelo.dto.endereco.EnderecoDTO;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class AtualizadorEnderecoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    private final EnderecoAtualizador atualizador = new EnderecoAtualizador();

    public Endereco atualizar(Long clienteId, EnderecoDTO dto) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        Endereco enderecoExistente = cliente.getEndereco();
        if (enderecoExistente == null) {
            throw new RecursoNaoEncontradoException("Endereco nao encontrado para este cliente");
        }

        Endereco novosDados = new Endereco();
        novosDados.setEstado(dto.getEstado());
        novosDados.setCidade(dto.getCidade());
        novosDados.setBairro(dto.getBairro());
        novosDados.setRua(dto.getRua());
        novosDados.setNumero(dto.getNumero());
        novosDados.setCodigoPostal(dto.getCodigoPostal());
        novosDados.setInformacoesAdicionais(dto.getInformacoesAdicionais());

        atualizador.atualizar(enderecoExistente, novosDados);
        clienteRepositorio.save(cliente);

        return enderecoExistente;
    }
}
