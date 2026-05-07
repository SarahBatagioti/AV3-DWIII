package com.autobots.automanager.servicos.endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.excecoes.ConflitoDeRecursoException;
import com.autobots.automanager.excecoes.RecursoNaoEncontradoException;
import com.autobots.automanager.modelo.dto.endereco.EnderecoDTO;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@Service
public class CadastradorEnderecoServico {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Endereco cadastrar(Long clienteId, EnderecoDTO dto) {
        Cliente cliente = clienteRepositorio.findById(clienteId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente nao encontrado"));

        if (cliente.getEndereco() != null) {
            throw new ConflitoDeRecursoException("Cliente ja possui endereco cadastrado");
        }

        Endereco endereco = new Endereco();
        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumero(dto.getNumero());
        endereco.setCodigoPostal(dto.getCodigoPostal());
        endereco.setInformacoesAdicionais(dto.getInformacoesAdicionais());

        cliente.setEndereco(endereco);
        Cliente clientePersistido = clienteRepositorio.saveAndFlush(cliente);

        return clientePersistido.getEndereco();
    }
}
