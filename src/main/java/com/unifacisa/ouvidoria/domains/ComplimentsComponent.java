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
 * Componente de elogios
 *  
 * @author Darllinson Azevedo
 */
@Component
public class ComplimentsComponent {
	@Autowired
	FeedbackRepository feedbackRepository;
	
	/**
	 * Buscar elogios no banco de dados
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param user Usuário autenticado
	 * @return Lista dos elogios
	 */
	public List<Feedback> getCompliments(User user) {
		Formatter.header("Elogios", 100);
		List<Feedback> compliments;
		
		if (user.getUserType() == UserTypes.ADMIN) {
			compliments = feedbackRepository.findByType(FeedbackTypes.COMPLIMENT);
			for (Feedback fb : compliments) {
				System.out.println(fb.toString());
			}
		} else {
			compliments = feedbackRepository.findByAuthorIdAndType(user.getId(), FeedbackTypes.COMPLIMENT);
			for (Feedback fb : compliments) {
				System.out.println(fb.toString());
			}
		}
		
		if (compliments.isEmpty()) System.out.println("Nao ha elogios.");
		return compliments;
	}
}
