package com.algaworks.algafood.domain.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CamposObrigatoriosException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.dto.EstadoDTO;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	public Estado buscar(Long id) {
		return estadoRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Estado de código %d não foi encontrado.", id)));
	}

	public Estado salvar(EstadoDTO estadoDTO) {
		if (StringUtils.isBlank(estadoDTO.getNome()))
			throw new CamposObrigatoriosException("O campo 'Nome' deve ser inserido");

		Estado estado = new Estado();
		BeanUtils.copyProperties(estadoDTO, estado);

		return estadoRepository.save(estado);
	}

	public Estado atualizar(Long estadoId, EstadoDTO estadoDTO) {
		if (StringUtils.isBlank(estadoDTO.getNome()))
			throw new CamposObrigatoriosException("O campo 'Nome' deve ser inserido");
		Estado estado = buscar(estadoId);

		BeanUtils.copyProperties(estadoDTO, estado);

		return estadoRepository.save(estado);
	}

	public void deletar(Long estadoId) {
		Estado estado = buscar(estadoId);

		estadoRepository.deleteById(estado.getId());
	}
}
