package com.integration.container.test.model;

import java.util.List;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedEntityGraph(name = "users-graph",
attributeNodes = @NamedAttributeNode(value = "userRoles", subgraph = "role-subgraph"),
			subgraphs = @NamedSubgraph(name = "role-subgraph", attributeNodes = @NamedAttributeNode("role")))
public class User {
	@Id
	@Column(name = "user_id", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Column(name = "user_name", nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "enabled")
	private String enabled;
	
	@Email
	@Column(name = "email")
	private String email;
	
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<UserRole> userRoles;
}