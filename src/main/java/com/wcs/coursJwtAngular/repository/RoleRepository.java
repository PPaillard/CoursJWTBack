package com.wcs.coursJwtAngular.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wcs.coursJwtAngular.models.ERole;
import com.wcs.coursJwtAngular.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	// Méthode construire permettant de retourner un rôle par son name
	Optional<Role> findByName(ERole name);
}