package com.mph.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OrderBy;

import jakarta.validation.constraints.Min;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="cart")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(nullable=false)
	@Min(0)
	@NonNull
	private Float amount;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
	@NonNull
	private User user;

	@OneToMany(targetEntity=ShoppingItem.class, mappedBy="cart", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy("id")
	private List<ShoppingItem> items;

	public void addItem(ShoppingItem item) {
		items.add(item);
	}

	public void removeItem(ShoppingItem item) {
		items.remove(item);
	}

	public void clearItems() {
		items.clear();
	}

}