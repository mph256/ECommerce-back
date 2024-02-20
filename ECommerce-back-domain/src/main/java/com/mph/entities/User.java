package com.mph.entities;

import java.util.List;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OrderBy;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="user")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=20, unique=true, nullable=false)
	@Size(min=3, max=20)
	@NotBlank
	@NonNull
	private String username;

	@Column(length=50)
	private String firstname;

	@Column(length=50)
	private String lastname;

	@Column(length=64, unique=true, nullable=false)
	@Size(min=3, max=64)
	@NotBlank
	@NonNull
	private String email;

	@Column(length=15)
	private String phone;

	@Column(length=255, nullable=false)
	@Size(min=8, max=255)
	@NotBlank
	@NonNull
	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name="registration_date", nullable=false)
	@NonNull
	private Date registrationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_connection", nullable=false)
	@NonNull
	private Date lastConnection;

	@OneToOne(targetEntity=ShoppingCart.class, mappedBy="user", cascade=CascadeType.ALL)
	private ShoppingCart cart;

	@OneToMany(targetEntity=Address.class, mappedBy="user", orphanRemoval=true, cascade=CascadeType.ALL)
	@OrderBy("name")
	private List<Address> addresses;

	@OneToMany(targetEntity=CreditCard.class, mappedBy="user", orphanRemoval=true, cascade=CascadeType.ALL)
	@OrderBy("expirationDate DESC")
	private List<CreditCard> creditCards;

	@OneToMany(targetEntity=Order.class, mappedBy="user", orphanRemoval=true, cascade=CascadeType.ALL)
	@OrderBy("orderDate DESC")
	private List<Order> orders;

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinTable(
		name="user_role",
		joinColumns=@JoinColumn(name="user_id"),
		inverseJoinColumns=@JoinColumn(name="role_id")
	)
	@OrderBy("name")
	@NotEmpty
	@NonNull
	private List<Role> roles;

	public void addRole(Role role) {
		roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}

}