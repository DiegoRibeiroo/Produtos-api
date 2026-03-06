package br.com.diego.produtos_api.mapper;

import br.com.diego.produtos_api.model.dto.request.ProdutoRequestDTO;
import br.com.diego.produtos_api.model.dto.response.ProdutoResponseDTO;
import br.com.diego.produtos_api.model.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    public Produto toEntity(ProdutoRequestDTO dto) {
        return Produto.builder()
                .nome(dto.getNome())
                .quantidade(dto.getQuantidade())
                .preco(dto.getPreco())
                .descricao(dto.getDescricao())
                .build();
    }

    public ProdutoResponseDTO toDTO(Produto produto) {
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .quantidade(produto.getQuantidade())
                .dataCriacao(produto.getDataCriacao())
                .build();
    }

    public void atualizarEntidade(ProdutoRequestDTO dto, Produto entidade) {
        entidade.setNome(dto.getNome());
        entidade.setDescricao(dto.getDescricao());
        entidade.setPreco(dto.getPreco());
        entidade.setQuantidade(dto.getQuantidade());
    }
}
