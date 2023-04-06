package com.unifacisa.ouvidoria.forms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unifacisa.ouvidoria.entities.User;
import com.unifacisa.ouvidoria.enums.UserTypes;
import com.unifacisa.ouvidoria.services.UserService;
import com.unifacisa.ouvidoria.utils.Formatter;
import com.unifacisa.ouvidoria.utils.Validator;

/**
 * Classe com controles de autenticação do usuário
 *
 * @author Darllinson Azevedo
 */
@Component
public class AuthForm {
	@Autowired
	UserService userService;
	
	public boolean userAuthenticated = false;
	public boolean isAdmin = false;
	public User userLogged = null;

	/**
	 * Autenticar usuário na aplicação
	 * 
	 * @author Darllinson Azevedo
	 */
	public void authentication() {
		while (!userAuthenticated) {
            System.out.println("\nVoce possui cadastro na plataforma?\n");
            String answer = Validator.readString("s para Sim / qualquer coisa para Nao: ");

            if (answer.equalsIgnoreCase("s")) login();
            else register();
        }
	}
	
	/**
	 * Formulário de autenticação de usuário
	 * 
	 * @author Darllinson Azevedo
	 */
	public void login() {
		Formatter.header("Fazer login na plataforma", 100);
		
		String username = Validator.readString("\nDigite seu nome de usuario: ");
		String password = Validator.readString("Digite sua senha: ");
		
		userLogged = userService.login(username, password);
		if (userLogged != null) {
			userAuthenticated = true;
			
			isAdmin = userLogged.getUserType() == UserTypes.ADMIN ? true : false;
		}
	}
	
	/**
	 * Formulário de registro de usuário
	 * 
	 * @author Darllinson Azevedo
	 */
	public void register() {
		Formatter.header("Faca seu cadastro", 100);
		
		String name = Validator.readString("\nDigite seu nome: ");
        String username = Validator.readString("Digite seu nome de usuario: ");
        String password = Validator.readString("Digite sua senha: ");
        
        String capitalizedName = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        
        User newUser = new User(null, capitalizedName, UserTypes.STUDENT, username, password);
        
        userService.addUser(newUser);
	}
	
	/**
	 * Desconectar usuário da aplicação
	 * 
	 * @author Darllinson Azevedo
	 */
	public void logout() {
		userAuthenticated = false;
		isAdmin = false;
		userLogged = null;
	}
}
