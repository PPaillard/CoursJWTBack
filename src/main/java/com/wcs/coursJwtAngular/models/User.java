package com.wcs.coursJwtAngular.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
/* En plus de spécifier le nom de la table qui  va être créé
 * On spécifie que le champs username & email doivent être unique
 * Aucun doublon ne sera toléré par MySQL dans ces colonnes
 * heureusement, puisque nous avons des méthodes qui récupère un user par son email ou son username
 * On risquerait d'en ramener 2 sinon.
*/
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Le nom d'utilisateur est obligatoire")
	@Size(max = 20)
	@NotNull
	private String username;

	@NotBlank(message = "Le mail est obligatoire")
	@Size(max = 50)
	@Email
	@NotNull
	private String email;

	@NotBlank(message = "Le mot de passe est obligatoire")
	@Size(max = 120)
	@NotNull
	private String password;

/*
 * Un user peut avoir plusieurs role, un role plusieurs user
 * On doit donc définir via anotation comment la relation se fait
 * On donne le nom de la table de jointure qui doit être créé
 * On indique la colonne qui va accueillir la clé primaire de la table User dans la table de jointure (user_id)
 * On indique la colonne qui va accueillir la clé primaire de la table Role dans la table de jointure (role_id)
 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	// Un User peut avoir plusieurs rôles, donc on utilise une structure permettant de recueillir plusieurs rôles.
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}