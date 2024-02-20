package com.mph.entities;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

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
@Table(name="payment_status")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class PaymentStatus {

	public enum PaymentStatusEnum {

		PENDING, RECEIVED, FAILED

	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(length=50, nullable=false)
	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	private PaymentStatusEnum name;

}