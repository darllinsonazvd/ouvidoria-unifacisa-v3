package com.unifacisa.ouvidoria.forms;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unifacisa.ouvidoria.entities.Feedback;
import com.unifacisa.ouvidoria.entities.User;
import com.unifacisa.ouvidoria.enums.FeedbackTypes;
import com.unifacisa.ouvidoria.services.FeedbackService;
import com.unifacisa.ouvidoria.utils.Formatter;
import com.unifacisa.ouvidoria.utils.Validator;

/**
 * Classe com métodos de controle da aplicação
 *
 * @author Darllinson Azevedo
 */
@Component
public class FeedbackForm {
	@Autowired
	FeedbackService feedbackService;
	
	private static final List<String> CATEGORIES = Arrays.asList("Reclamacao", "Elogio", "Ideia");
	
	/**
     * Recuperar feedbacks do usuário autenticado, se for admin, recupera todos os feedbacks
     *
     * @author Darllinson Azevedo
     *
     * @param userLogged Usuário autenticado
     */
    public void getFeedbacks(User userLogged) {
        feedbackService.getFeedbacks(userLogged.getId(), FeedbackTypes.ALL);
    }
    
    /**
     * Formulário para o usuário adicionar o feedback
     *
     * @author Darllinson Azevedo
     *
     * @param userLogged Usuário autenticado
     */
    public void addFeedbackForm(User userLogged) {
        Formatter.menu("Categorias:", CATEGORIES);

        int optionToAdd = Validator.readInt("\nQual a categoria do feedback deseja adicionar? (1 / 2 / 3): ");

        switch (optionToAdd) {
            case 1 -> {
                String claim = Validator.readString("\nDigite sua reclamacao:\n");
                Feedback fb = new Feedback(null, userLogged, FeedbackTypes.CLAIM, claim);
                feedbackService.addFeedback(fb);
            }
            case 2 -> {
                String compliment = Validator.readString("\nDigite seu elogio:\n");
                Feedback fb = new Feedback(null, userLogged, FeedbackTypes.COMPLIMENT, compliment);
                feedbackService.addFeedback(fb);
            }
            case 3 -> {
                String idea = Validator.readString("\nDigite sua ideia:\n");
                Feedback fb = new Feedback(null, userLogged, FeedbackTypes.IDEA, idea);
                feedbackService.addFeedback(fb);
            }
        }
    }
    
    /**
     * Formulário para o usuário excluir um feedback
     *
     * @author Darllinson Azevedo
     *
     * @param userLogged Usuário autenticado
     */
    public void deleteFeedbackForm(User userLogged) {
        Formatter.menu("Categorias:", CATEGORIES);
        int optionToRemove = Validator.readInt("\nQual a categoria do feedback deseja remover? (1 / 2 / 3): ");

        switch (optionToRemove) {
            case 1 -> {
                System.out.println("\nQual reclamacao deseja remover?\n");
                feedbackService.getFeedbacks(userLogged.getId(), FeedbackTypes.CLAIM);

                int claimId = Validator.readInt("\nNumero da reclamacao: ");
                feedbackService.deleteFeedback(claimId, FeedbackTypes.CLAIM);
            }
            case 2 -> {
                System.out.println("\nQual elogio deseja remover?\n");
                feedbackService.getFeedbacks(userLogged.getId(), FeedbackTypes.COMPLIMENT);

                int complimentId = Validator.readInt("\nNumero do elogio: ");
                feedbackService.deleteFeedback(complimentId, FeedbackTypes.COMPLIMENT);
            }
            case 3 -> {
                System.out.println("\nQual ideia deseja remover?\n");
                feedbackService.getFeedbacks(userLogged.getId(), FeedbackTypes.IDEA);

                int ideaId = Validator.readInt("\nNumero da ideia: ");
                feedbackService.deleteFeedback(ideaId, FeedbackTypes.IDEA);
            }
            default -> Formatter.errorEmitter("Categoria nao encontrada!");
        }
    }
    
    /**
     * Formulário para o usuário editar um feedback
     *
     * @author Darllinson Azevedo
     *
     * @param userLogged Usuário autenticado
     */
    public void editFeedbackForm(User userLogged) {
        Formatter.menu("Categorias:", CATEGORIES);
        int optionToEdit = Validator.readInt("\nQual a categoria do feedback deseja editar? (1 / 2 / 3): ");

        switch (optionToEdit) {
            case 1 -> {
                System.out.println("\nQual reclamacao deseja editar?\n");
                feedbackService.getFeedbacks(userLogged.getId(), FeedbackTypes.CLAIM);
                int claimId = Validator.readInt("\nNumero da reclamacao: ");
                
                feedbackService.editFeedback(userLogged, FeedbackTypes.CLAIM, claimId);
            }
            case 2 -> {
                System.out.println("\nQual elogio deseja editar?\n");
                feedbackService.getFeedbacks(userLogged.getId(), FeedbackTypes.COMPLIMENT);
                int complimentId = Validator.readInt("\nNumero do elogio: ");

                feedbackService.editFeedback(userLogged, FeedbackTypes.COMPLIMENT, complimentId);
            }
            case 3 -> {
                System.out.println("\nQual ideia deseja editar?\n");
                feedbackService.getFeedbacks(userLogged.getId(), FeedbackTypes.IDEA);
                int ideaId = Validator.readInt("\nNumero da ideia: ");

                feedbackService.editFeedback(userLogged, FeedbackTypes.IDEA, ideaId);
            }
            default -> Formatter.errorEmitter("Categoria nao encontrada!");
        }
    }
}
