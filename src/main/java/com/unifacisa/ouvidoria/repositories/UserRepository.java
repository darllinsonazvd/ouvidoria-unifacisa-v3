package com.unifacisa.ouvidoria.repositories;

import org.springframework.data.repository.CrudRepository;

import com.unifacisa.ouvidoria.entities.User;

/**
 * Repositório da entidade User
 * 
 * @author Darllinson Azevedo
 */
public interface UserRepository extends CrudRepository<User, Integer> {
	/**
	 * Encontrar usuário no sistema pelo username
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param username Nome de usuário
	 * @return Usuário, se não for encontrado no sistema, retorna null
	 */
	User findByUsername(String username);
}
