package com.mph.entities;

import java.util.Date;

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
@Table(name="promotion")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@NonNull
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class Promotion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=20, unique=true, nullable=false)
	@Size(min=3, max=20)
	@NotBlank
	@NonNull
	private String code;

	@Column(length=2, nullable=false)
	@Min(5)
	@Max(50)
	@NonNull
	private Integer percentage;

	@Column(name="expiration_date", nullable=false)
	@NonNull
	private Date expirationDate;

}