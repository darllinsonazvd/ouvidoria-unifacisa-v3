package com.unifacisa.ouvidoria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.unifacisa.ouvidoria.domains.ClaimsComponent;
import com.unifacisa.ouvidoria.domains.ComplimentsComponent;
import com.unifacisa.ouvidoria.domains.IdeasComponent;
import com.unifacisa.ouvidoria.entities.Feedback;
import com.unifacisa.ouvidoria.entities.User;
import com.unifacisa.ouvidoria.enums.FeedbackTypes;
import com.unifacisa.ouvidoria.enums.UserTypes;
import com.unifacisa.ouvidoria.repositories.FeedbackRepository;
import com.unifacisa.ouvidoria.repositories.UserRepository;
import com.unifacisa.ouvidoria.utils.Formatter;
import com.unifacisa.ouvidoria.utils.Validator;

/**
 * Serviço de feedbacks da aplicação
 *
 * @author Darllinson Azevedo
 */
@Service
public class FeedbackService {
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClaimsComponent claimsComponent;
	
	@Autowired
	ComplimentsComponent complimentsComponent;
	
	@Autowired
	IdeasComponent ideasComponent;

	/**
	 * Recuperar feedbacks do usuário, caso for admin, recupera de todos os usuários
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param userId Id do usuário autenticado
	 * @param type Tipo dos feedbacks
	 */
	public void getFeedbacks(Integer userId, FeedbackTypes type) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado!"));
		
		switch (type) {
			case CLAIM -> claimsComponent.getClaims(user);
			case COMPLIMENT -> complimentsComponent.getCompliments(user);
			case IDEA -> ideasComponent.getIdeas(user);
			default -> {
				claimsComponent.getClaims(user);
				complimentsComponent.getCompliments(user);
				ideasComponent.getIdeas(user);
			}
		}
	}

	/**
	 * Adicionar feedback no banco de dados
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param fb Feedback para ser adicionado
	 */
	public void addFeedback(Feedback fb) {
		try {
			feedbackRepository.save(fb);
			Formatter.successEmitter("Obrigado pelo feedback!");
		} catch (IllegalArgumentException err) {
			Formatter.errorEmitter("Nao foi possivel adicionar seu feedback. Contate o admin do sistema");
		} catch (OptimisticLockingFailureException err) {
			Formatter.errorEmitter("Nao foi possivel adicionar seu feedback. Contate o admin do sistema");
		}
	}
	
	/**
	 * Apagar um feedback
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param id Id do feedback
	 * @param type Tipo do feedback
	 */
	public void deleteFeedback(Integer id, FeedbackTypes type) {
		try {
			Feedback fb = feedbackRepository.findByIdAndType(id, type);
			if (fb == null) throw new IllegalArgumentException("Feedback nao encontrado!");
			
			feedbackRepository.delete(fb);
			Formatter.successEmitter("Feedback apagado com sucesso!");
		} catch (IllegalArgumentException err) {
			Formatter.errorEmitter(err.getMessage());
		}
	}
	
	/**
	 * Editar comentário de um feedback
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param user Usuário autenticado
	 * @param type Tipo do feedback
	 * @param id Id do feedback
	 */
	public void editFeedback(User user, FeedbackTypes type, Integer id) {
		try {
			Feedback selectedFb;
			
			if (user.getUserType() == UserTypes.ADMIN) {
				selectedFb = feedbackRepository.findByIdAndType(id, type);
				if (selectedFb == null) throw new IllegalArgumentException("Feedback nao encontrado!");
			} else {
				selectedFb = feedbackRepository.findByIdAndTypeAndAuthorId(user.getId(), type, id);
				if (selectedFb == null) throw new IllegalArgumentException("Feedback nao encontrado!");
			}
			
			String newDescription = Validator.readString("Digite seu novo feedback:\n");
			
			selectedFb.setDescription(newDescription);
			feedbackRepository.save(selectedFb);
			Formatter.successEmitter("Feedback editado com sucesso!");
		} catch (IllegalArgumentException err) {
			Formatter.errorEmitter(err.getMessage());
		}
	}
}
