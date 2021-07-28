package com.wcs.coursJwtAngular.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wcs.coursJwtAngular.models.Role;
import com.wcs.coursJwtAngular.models.User;

/*
 * Classe dont se servira Spring Security pour verifier le détail du User ainsi que ses rôles.
 * Elle doit obligatoirement implémenter UserDetails de Spring Security (qui oblige à définir une méthode build()
 */
public class UserDetailsImpl implements UserDetails {
	/*
	 *  On ne va pas rentrer dans les détails, mais la classe UserDetails est sérialisable
	 *  C'est à dire qu'elle doit pouvoir être transporter à travers le réseau et donc convertis en chaine
	 *  Pour se faire, on doit définir un serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String email;

	/*
	 * L'objet UserDetailsImp va servir pour renvoyer les droits du user ainsi que ses infos
	 * On va donc devoir le convertir en json pour l'envoyer.
	 * Durant cette conversion, on ne soit pas joindre le mot de passe 
	 * Il ne DOIT PAS être renvoyer au FRONT pour des raisons évidentes
	 */
	@JsonIgnore
	private String password;

	/*
	 * Spring security a besoin que notre UserDetailsImp implémentant UserDetails
	 * Contienne une liste d'authorités (rôles) avec des méthodes précises pour les gérer
	 */
	private Collection<? extends GrantedAuthority> authorities;

	/*
	 * Contrairement au @Bean ou au @Entity, nous avons besoin d'un constructeur totalement remplis
	 * Pour pouvoir renvoyer un UserDetailsImp à SpringSecurity
	 */
	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	/*
	 * La méthode la plus simple pour construire un objet UserDetailsImp et de le construire grâce à un objet User
	 * On va donc simplement convertir le Set<Role> roles de notre User en Collection<? extends GrantedAuthority> authorities;
	 * Pour convertir le Set en Collection (plus précisement en List qui étends collection
	 * Nous allons effectuer une gymnastique que je n'expliquerais pas plus ici.
	 * Notre méthode reçoit donc un User en paramètre et renvoi un nouvel objet UserDetailsImp construit sur la base du User
	 */
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/*
	 * Méthode à @Override venant de la classe qu'on implémente : UserDetails
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}