package com.mph.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="address")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=50, nullable=false)
	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	private String name;

	@Column(length=255, nullable=false)
	@Size(min=3, max=255)
	@NotBlank
	@NonNull
	private String street;

	@Column(length=139, nullable=false)
	@Size(min=3, max=139)
	@NotBlank
	@NonNull
	private String city;

	@Column(length=5, nullable=false)
	@NonNull
	private Integer zipcode;

	@Column(name="is_default_shipping", nullable=false)
	@NonNull
	private Boolean isDefaultShipping;

	@Column(name="is_default_billing", nullable=false)
	@NonNull
	private Boolean isDefaultBilling;

	@Column(name="is_active", nullable=false)
	@NonNull
	private Boolean isActive;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="country_id", nullable=false)
	@NonNull
	private Country country;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	@NonNull
	private User user;

}