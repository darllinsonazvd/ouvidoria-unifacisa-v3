package com.unifacisa.ouvidoria.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.unifacisa.ouvidoria.entities.Feedback;
import com.unifacisa.ouvidoria.enums.FeedbackTypes;

/**
 * Repositório dos Feedbacks
 * 
 * @author Darllinson Azevedo
 */
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {
	/**
	 * Filtrar feedbacks pelo tipo
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param type Tipo do feedback
	 * @return Lista dos feedbacks filtrados pelo tipo
	 */
	List<Feedback> findByType(FeedbackTypes type);
	
	/**
	 * Filtrar feedbacks por autor
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param userId
	 * @return Lista dos feedbacks filtrados pelo autor
	 */
	List<Feedback> findByAuthorId(Integer userId);
	
	/**
	 * Filtrar feedbacks por autor e pelo tipo
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param userId Id do usuário
	 * @param type Tipo do feedback
	 * @return Lista dos feedbacks filtrados pelo autor e pelo tipo
	 */
	List<Feedback> findByAuthorIdAndType(Integer userId, FeedbackTypes type);
	
	/**
	 * Encontrar feedback pelo id e tipo
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param feedbackId Id do feedback
	 * @param type Tipo do feedback
	 * @return Feedback selecionado pelo id e tipo
	 */
	Feedback findByIdAndType(Integer feedbackId, FeedbackTypes type);
	
	/**
	 * Encontrar feedback de um usuário pelo id e tipo
	 * 
	 * @author Darllinson Azevedo
	 *
	 * @param feedbackId Id do feedback
	 * @param type Tipo do feedback
	 * @param userId Id do usuário
	 * @return Feedback selecionado pelo id, tipo e fitrado por usuário
	 */
	Feedback findByIdAndTypeAndAuthorId(Integer feedbackId, FeedbackTypes type,  Integer userId);
}
