package br.com.diego.produtos_api.service;

import br.com.diego.produtos_api.exception.BusinessRuleException;
import br.com.diego.produtos_api.exception.EntityNotFoundException;
import br.com.diego.produtos_api.exception.MensagensErro;
import br.com.diego.produtos_api.mapper.ProdutoMapper;
import br.com.diego.produtos_api.model.dto.request.ProdutoRequestDTO;
import br.com.diego.produtos_api.model.dto.request.ProdutoVendaRequestDTO;
import br.com.diego.produtos_api.model.dto.response.ProdutoResponseDTO;
import br.com.diego.produtos_api.model.entity.Produto;
import br.com.diego.produtos_api.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Transactional
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoMapper.toEntity(produtoRequestDTO);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(produtoSalvo);
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = buscarProdutoNoBanco(id);
        return produtoMapper.toDTO(produto);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produtoExistente = buscarProdutoNoBanco(id);

        produtoMapper.atualizarEntidade(produtoRequestDTO, produtoExistente);

        Produto produtoAtualizado = produtoRepository.save(produtoExistente);
        return produtoMapper.toDTO(produtoAtualizado);
    }

    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = buscarProdutoNoBanco(id);
        produtoRepository.delete(produto);
    }

    @Transactional
    public ProdutoResponseDTO venderProduto(Long id, ProdutoVendaRequestDTO vendaDTO) {
        Produto produto = buscarProdutoNoBanco(id);

        //caso a quantidade da venda seja maior que o estoque
        if (produto.getQuantidade() < vendaDTO.getQuantidade()) {
            throw new BusinessRuleException(MensagensErro.VENDA_ESTOQUE_INSUFICIENTE + produto.getQuantidade());
        }

        produto.setQuantidade(produto.getQuantidade() - vendaDTO.getQuantidade());
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(produtoSalvo);
    }


    private Produto buscarProdutoNoBanco(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MensagensErro.PRODUTO_NAO_ENCONTRADO));
    }
}
