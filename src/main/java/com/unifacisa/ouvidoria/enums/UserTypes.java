package com.unifacisa.ouvidoria.enums;

public enum UserTypes {
	ADMIN, STUDENT;
	
	/**
     * Traduzir tipos dos usuários para PT-BR
     *
     * @author Darllinson Azevedo
     *
     * @return Tipo do usuário em português
     */
    @Override
    public String toString() {
        return switch (this.name()) {
            case "ADMIN" -> "Admin";
            case "STUDENT" -> "Aluno";
            default -> "Invalid key: " + this.name();
        };
    }
}
