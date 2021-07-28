package com.wcs.coursJwtAngular.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcs.coursJwtAngular.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// On construit une méthode avec JPA pour aller chercher un User par son username
	// Cela est possible car le username est défini en "Unique" dans la table User
	Optional<User> findByUsername(String username);

	// Méthode qui permet de savoir si un User est déjà existant avec ce username
	// (utile pour savoir si on autorise l'inscription avec le username envoyé)
	Boolean existsByUsername(String username);

	// Méthode qui permet de savoir si un User est déjà existant avec ce email
	// (utile pour savoir si on autorise l'inscription avec le email envoyé)
	Boolean existsByEmail(String email);
}
