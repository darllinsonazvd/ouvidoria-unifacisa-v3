package com.unifacisa.ouvidoria.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unifacisa.ouvidoria.entities.User;
import com.unifacisa.ouvidoria.enums.UserTypes;
import com.unifacisa.ouvidoria.forms.AuthForm;
import com.unifacisa.ouvidoria.forms.FeedbackForm;
import com.unifacisa.ouvidoria.services.UserService;
import com.unifacisa.ouvidoria.utils.Formatter;
import com.unifacisa.ouvidoria.utils.Validator;

@Component
public class AppComponent {
	@Autowired
	UserService userService;
	
	@Autowired
	AuthForm authForm;
	
	@Autowired
	FeedbackForm fbForms;
	public void start() {
		
		boolean running = true;
		
		boolean userAuthenticated;
		boolean isAdmin;
		User userLogged;
		
		final List<String> OPTIONS_ADMIN = Arrays.asList(
                "Listar feedbacks",
                "Adicionar feedback",
                "Remover feedback",
                "Editar feedback",
                "Sair\n"
        );

        final List<String> OPTIONS_USER = Arrays.asList(
                "Listar feedbacks",
                "Adicionar feedback",
                "Editar feedback",
                "Sair\n"
        );
		
		/** Adicionando admin na base dados do sistema */
		final User admin = new User(null, "Diego Braga", UserTypes.ADMIN, System.getenv("ADMIN_USERNAME"), System.getenv("ADMIN_PASSWORD"));
		userService.addUser(admin);
		
		while (running) {
			Formatter.header("Bem-vindo ao Sistema de Ouvidoria da Unifacisa!", 100);
			
			authForm.authentication();
			userAuthenticated = authForm.userAuthenticated;
			userLogged = authForm.userLogged;
			isAdmin = authForm.isAdmin;
			
			while (userAuthenticated) {
				Formatter.header("Bem-vindo(a) " + userLogged.getName(), 100);
				Formatter.line(100);
				if (isAdmin) Formatter.menu("Selecione uma opcao:", OPTIONS_ADMIN);
                else Formatter.menu("Selecione uma opcao:", OPTIONS_USER);
				Formatter.line(100);
				
				int option = Validator.readInt("\nOpcao: ");
				
				switch (option) {
					/** Exibir todos os feedbacks */
					case 1 -> fbForms.getFeedbacks(userLogged);
					
					/** Adicionar feedback pro categoria */
					case 2 -> fbForms.addFeedbackForm(userLogged);
					
					/** Admin -> Delete feedback
                    User -> Edit yours feedbacks */
					case 3 -> {
						if (isAdmin) fbForms.deleteFeedbackForm(userLogged);
						else fbForms.editFeedbackForm(userLogged);
					}
					
					/** Admin -> Edit all users feedbacks
                    User -> Quit */
					case 4 -> {
						if (isAdmin) fbForms.editFeedbackForm(userLogged);
						else {
	                       Formatter.header("Obrigado por utilizar o Sistema de Ouvidoria da Unifacisa!", 100);
	                       userAuthenticated = false;
	                       authForm.logout();
						}
					}
					
					/** Admin -> Quit
                    User -> Invalid option */
					case 5 -> {
						if (isAdmin) {
                           Formatter.header("Obrigado por utilizar o Sistema de Ouvidoria da Unifacisa!", 100);
                           userAuthenticated = false;
                           authForm.logout();
						} else Formatter.errorEmitter("Opcao invalida!");
					}
					
					/** Opção inválida */
                    default -> Formatter.errorEmitter("Opcao invalida!");
				}
			}
		}
	}
}
