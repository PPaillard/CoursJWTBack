package com.wcs.coursJwtAngular.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wcs.coursJwtAngular.models.User;
import com.wcs.coursJwtAngular.repository.UserRepository;

/*
 * La classe doit implémenter UserDetailsService (classe de Spring Security, encore, oui oui)
 * UserDetailsImpl n'est pas une entity, du coup on ne peut pas faire de repo pour la récupèrer ou la générer.
 * On doit doit créer un service qui, lui, va utiliser le UserRepository
 * Allez chercher le User que l'ont veut et utiliser la méthode de UserDetailsImp pour construire un UserDetailsImp
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	/*
	 * On @Override la méthode existante dans UserDetailsService 
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec le nom : " + username));

		return UserDetailsImpl.build(user);
	}

}