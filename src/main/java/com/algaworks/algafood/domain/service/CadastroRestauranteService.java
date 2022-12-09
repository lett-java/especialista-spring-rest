package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.dto.RestauranteDTO;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Restaurante de código %d não foi encontrado.", id)));
	}

	public Restaurante salvar(RestauranteDTO restauranteDTO) {
		Cozinha cozinha = cozinhaService.buscar(restauranteDTO.getCozinha().getId());

		restauranteDTO.setCozinha(cozinha);

		Restaurante restaurante = new Restaurante();
		BeanUtils.copyProperties(restauranteDTO, restaurante);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante atualizar(Long id, RestauranteDTO restauranteDTO) {
		Cozinha cozinha = cozinhaService.buscar(restauranteDTO.getCozinha().getId());

		Restaurante entityToUpdate = buscar(id);

		restauranteDTO.setCozinha(cozinha);
		BeanUtils.copyProperties(restauranteDTO, entityToUpdate);

		return restauranteRepository.save(entityToUpdate);
	}

	public void excluir(Long restauranteId) {
		try {
			restauranteRepository.deleteById(restauranteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de restaurante com o código %d.", restauranteId));
		}
	}

	public Restaurante merge(Map<String, Object> entity, Long restauranteId) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante entityValue = objectMapper.convertValue(entity, Restaurante.class);
		Restaurante entityToUpdate = buscar(restauranteId);

		entity.forEach((chave, valor) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, chave);
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, entityValue);

			System.out.println(chave + " = " + valor);

			ReflectionUtils.setField(field, entityToUpdate, novoValor);
		});

		return atualizarParcialmente(restauranteId, entityToUpdate);
	}

	public Restaurante atualizarParcialmente(Long id, Restaurante restaurante) {
		Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());

		Restaurante entityToUpdate = buscar(id);

		restaurante.setCozinha(cozinha);
		BeanUtils.copyProperties(restaurante, entityToUpdate);

		return restauranteRepository.save(entityToUpdate);
	}

}
