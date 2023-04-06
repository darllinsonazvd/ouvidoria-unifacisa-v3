package com.unifacisa.ouvidoria.domains;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unifacisa.ouvidoria.entities.Feedback;
import com.unifacisa.ouvidoria.entities.User;
import com.unifacisa.ouvidoria.enums.FeedbackTypes;
import com.unifacisa.ouvidoria.enums.UserTypes;
import com.unifacisa.ouvidoria.repositories.FeedbackRepository;
import com.unifacisa.ouvidoria.utils.Formatter;

/**
 * Componente de ideias
 *  
 * @author Darllinson Azevedo
 */
@Component
public class IdeasComponent {
	@Autowired
	FeedbackRepository feedbackRepository;
	
	/**
	 * Buscar ideias no banco de dados
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param user Usuário autenticado
	 * @return Lista das ideias
	 */
	public List<Feedback> getIdeas(User user) {
		Formatter.header("Ideias", 100);
		List<Feedback> ideas;
		
		if (user.getUserType() == UserTypes.ADMIN) {
			ideas = feedbackRepository.findByType(FeedbackTypes.IDEA);
			for (Feedback fb : ideas) {
				System.out.println(fb.toString());
			}
		} else {
			ideas = feedbackRepository.findByAuthorIdAndType(user.getId(), FeedbackTypes.IDEA);
			for (Feedback fb : ideas) {
				System.out.println(fb.toString());
			}
		}
		
		if (ideas.isEmpty()) System.out.println("Nao ha ideias.");
		return ideas;
	}
}
