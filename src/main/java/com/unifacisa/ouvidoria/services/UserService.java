package com.unifacisa.ouvidoria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unifacisa.ouvidoria.entities.User;
import com.unifacisa.ouvidoria.enums.UserTypes;
import com.unifacisa.ouvidoria.repositories.UserRepository;
import com.unifacisa.ouvidoria.utils.Formatter;

/**
 * Serviço de usuários da aplicação
 *
 * @author Darllinson Azevedo
 */
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	/**
	 * Cadastrar usuário no sistema
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param user Usuário para ser cadastrado
	 * @return String com status do cadastro do usuário
	 */
	public void addUser(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser == null) {
			BCryptPasswordEncoder crypto = new BCryptPasswordEncoder();
			String encryptedPassword = crypto.encode(user.getPassword());
			user.setPassword(encryptedPassword);

			userRepository.save(user);
			
			if (user.getUserType() == UserTypes.STUDENT)
				Formatter.successEmitter("Cadastro feito com sucesso. Faca seu login!");
		} else {
			if (user.getUserType() == UserTypes.STUDENT)
				Formatter.errorEmitter("Nome de usuario indisponivel.");
		}
	}

	/**
	 * Verificar login do usuário
	 * 
	 * @author Darllinson Azevedo
	 * 
	 * @param username Username do usuário
	 * @param password Senha do usuário
	 * @return Usuário autenticado
	 */
	public User login(String username, String password) {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			Formatter.errorEmitter("Nome de usuario ou senha invalidos.");
			return null;
		} else {
			BCryptPasswordEncoder crypto = new BCryptPasswordEncoder();
			
			if (crypto.matches(password, user.getPassword())) {
				Formatter.successEmitter("Logado na plataforma!");
				return user;
			}

			Formatter.errorEmitter("Nome de usuario ou senha invalidos.");
			return null;
		}
	}
}
