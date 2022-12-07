package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	public List<Restaurante> listar() {
		return restauranteRepository.listar();
	}

	public Restaurante buscar(Long id) {
		return restauranteRepository.buscar(id);
	}

	public Restaurante salvar(RestauranteDTO restauranteDTO) {
		Cozinha cozinha = cozinhaService.buscar(restauranteDTO.getCozinha().getId());

		if (cozinha == null)
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", restauranteDTO.getCozinha().getId()));

		restauranteDTO.setCozinha(cozinha);

		Restaurante restaurante = new Restaurante();
		BeanUtils.copyProperties(restauranteDTO, restaurante);

		return restauranteRepository.salvar(restaurante);
	}

	public Restaurante atualizar(Long id, RestauranteDTO restauranteDTO) {
		Cozinha cozinha = cozinhaService.buscar(restauranteDTO.getCozinha().getId());
		if (cozinha == null)
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com código %d", restauranteDTO.getCozinha().getId()));

		Restaurante entityToUpdate = buscar(id);
		if (entityToUpdate == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de restaurante com código %d", id));
		}

		restauranteDTO.setCozinha(cozinha);
		BeanUtils.copyProperties(restauranteDTO, entityToUpdate);

		return restauranteRepository.salvar(entityToUpdate);
	}

	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.remover(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de restaurante com o código %d.", restauranteId));
		}
	}

}
