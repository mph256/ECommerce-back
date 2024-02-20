package com.mph.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Table(name="credit_card")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class CreditCard {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=19, unique=true, nullable=false)
	@NonNull
	private Long number;

	@Column(name="holder_name", length=50, nullable=false)
	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	private String holderName;

	@Temporal(TemporalType.DATE)
	@Column(name="expiration_date", nullable=false)
	@NonNull
	private Date expirationDate;

	@Column(length=3, nullable=false)
	@NonNull
	private Integer CVC;

	@Column(name="is_default", nullable=false)
	@NonNull
	private Boolean isDefault;

	@Column(name="is_active", nullable=false)
	@NonNull
	private Boolean isActive;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="credit_card_type_id", nullable=false)
	@NonNull
	private CreditCardType type;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	@NonNull
	private User user;

}