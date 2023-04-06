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

@Component
public class ClaimsComponent {
	@Autowired
	FeedbackRepository feedbackRepository;
	
	public void getClaims(User user) {
		Formatter.header("Reclamacoes", 100);
		List<Feedback> claims;
		
		if (user.getUserType() == UserTypes.ADMIN) {
			claims = feedbackRepository.findByType(FeedbackTypes.CLAIM);
			for (Feedback fb : claims) {
				System.out.println(fb.toString());
			}
		} else {
			claims = feedbackRepository.findByAuthorIdAndType(user.getId(), FeedbackTypes.CLAIM);
			for (Feedback fb : claims) {
				System.out.println(fb.toString());
			}
		}
		
		if (claims.isEmpty()) System.out.println("Nao ha reclamacoes.");
	}
}
