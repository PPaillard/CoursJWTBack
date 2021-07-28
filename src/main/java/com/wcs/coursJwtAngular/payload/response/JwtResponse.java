package com.wcs.coursJwtAngular.payload.response;

import java.util.List;

/*
 * Classe qui sera utilisé pour encapsuler la réponse au moment du login
 * Elle contient toutes les informations qui nécessite d'être stocké coté FRONT
 * (username, mail, liste des rôles du User
 * Ainsi que le Token JWT généré 
 */
public class JwtResponse {
	private String token;
	/*
	 * On renvoi le type du Token attendu ("Bearer")
	 */
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
}
