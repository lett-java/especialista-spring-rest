package com.algaworks.algafood.domain.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CamposObrigatoriosException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.dto.CidadeDTO;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService estadoService;

	public List<Cidade> listar() {
		return cidadeRepository.listar();
	}

	public Cidade buscar(Long cidadeId) {
		return cidadeRepository.buscar(cidadeId);
	}

	public Cidade adicionar(CidadeDTO cidadeDTO) {
		if (StringUtils.isBlank(cidadeDTO.getNome()))
			throw new CamposObrigatoriosException("Campo 'Nome' deve ser preenchido.");

		if (cidadeDTO.getEstado() == null)
			throw new CamposObrigatoriosException("Campo 'Estado' deve ser preenchido com id.");

		Estado estado = estadoService.buscar(cidadeDTO.getEstado().getId());
		if (estado == null)
			throw new EntidadeNaoEncontradaException(
					String.format("Estado de código %d não foi encontrado.", cidadeDTO.getEstado().getId()));

		Cidade cidade = new Cidade();
		BeanUtils.copyProperties(cidadeDTO, cidade);

		cidade.setEstado(estado);

		return cidadeRepository.salvar(cidade);
	}

	public Cidade atualizar(Long cidadeId, CidadeDTO cidadeDTO) {
		if (StringUtils.isBlank(cidadeDTO.getNome()))
			throw new CamposObrigatoriosException("Campo 'Nome' deve ser preenchido.");

		Cidade cidade = buscar(cidadeId);
		if (cidade == null)
			throw new EntidadeNaoEncontradaException(
					String.format("Cidade de código %d não foi encontrado.", cidadeId));

		BeanUtils.copyProperties(cidadeDTO, cidade, "estado");

		return cidadeRepository.salvar(cidade);

	}

	public void deletar(Long cidadeId) {
		Cidade cidade = buscar(cidadeId);
		if (cidade == null)
			throw new EntidadeNaoEncontradaException(String.format("Cidade de código %d não foi encontrada.", cidadeId));
		
		cidadeRepository.remover(cidade);
	}

}
