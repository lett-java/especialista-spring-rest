package com.algaworks.algafood.domain.service;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.lang.reflect.Field;
import java.math.BigDecimal;
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

	public List<Restaurante> restaurantePorTaxaFrente(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	public List<Restaurante> restaurantesPorNomeAndCozinhaId(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}

	public Restaurante restaurantePorNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContainingIgnoreCase(nome)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Restaurante contendo no nome %s, não foi encontrado", nome)));

	}

	public List<Restaurante> restaurantesTopDoisPorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContainingIgnoreCase(nome);
	}

	public Boolean existsByNome(String nome) {
		return restauranteRepository.existsByNomeIgnoreCase(nome);
	}

	public Integer countByCozinha(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	public List<Restaurante> restaurantePorNomeAndTaxaFrente(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}

	public List<Restaurante> findAllComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}

	public Restaurante buscarPrimeiro() {
		return restauranteRepository.buscarPrimeiro().get();
	}

}
