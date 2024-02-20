package com.mph.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import jakarta.validation.constraints.Min;
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
@Table(name="delivery_option")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class DeliveryOption {

	public enum DeliveryOptionEnum {

		STANDARD, EXPRESS

	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(length=50, nullable=false)
	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	private DeliveryOptionEnum name;

	@Column(nullable=false)
	@Min(1)
	@NonNull
	private Float price;

	@Column(nullable=false)
	@Min(1)
	@NonNull
	private Integer time;

}