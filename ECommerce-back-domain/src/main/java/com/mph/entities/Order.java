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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OrderBy;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="order")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(nullable=false)
	@NonNull
	private Float amount;

	@Temporal(TemporalType.DATE)
	@Column(name="order_date", nullable=false)
	@NonNull
	private Date orderDate;

	@Temporal(TemporalType.DATE)
	@Column(name="delivery_date", nullable=false)
	@NonNull
	private Date deliveryDate;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="delivery_option_id", nullable=false)
	@NonNull
	private DeliveryOption deliveryOption;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="shipping_address_id", nullable=false)
	@NonNull
	private Address shippingAddress;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="billing_address_id", nullable=false)
	@NonNull
	private Address billingAddress;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="promotion_id")
	private Promotion promotion;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	@NonNull
	private User user;

	@OneToMany(targetEntity=Suborder.class, mappedBy="order", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@NonNull
	private List<Suborder> suborders;

	@OneToMany(targetEntity=Payment.class, mappedBy="order", cascade=CascadeType.ALL)
	@OrderBy("date DESC")
	@NonNull
	private List<Payment> payments;

	public void addSuborder(Suborder suborder) {
		suborders.add(suborder);
	}

	public void removeSuborder(Suborder suborder) {
		suborders.remove(suborder);
	}

	public void addPayment(Payment payment) {
		payments.add(payment);
	}

}