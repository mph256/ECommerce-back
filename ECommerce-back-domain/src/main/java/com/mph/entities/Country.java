package com.mph.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
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
@Table(name="country")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class Country {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=52, unique=true, nullable=false)
	@Size(min=3, max=52)
	@NotBlank
	@NonNull
	private String name;

	@Column(name="numeric_code", length=3, unique=true, nullable=false)
	@NonNull
	private Integer numericCode;

	@Column(name="alpha_2_code", length=2, unique=true, nullable=false)
	@Size(min=2, max=2)
	@NotBlank
	@NonNull
	private String alpha2Code;

	@Column(name="alpha_3_code", length=3, unique=true, nullable=false)
	@Size(min=3, max=3)
	@NotBlank
	@NonNull
	private String alpha3Code;

	@Column(length=3, nullable=false)
	@Min(0)
	@Max(100)
	@NonNull
	private Float VAT;

}