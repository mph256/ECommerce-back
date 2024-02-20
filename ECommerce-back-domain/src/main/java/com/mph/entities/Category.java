package com.mph.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OrderBy;

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
@Table(name="category")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class Category {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=50, unique=true, nullable=false)
	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	private String name;

	@Column(columnDefinition="clob", nullable=false)
	@NonNull
	private byte[] image;

	@Column(name="is_active", nullable=false)
	@NonNull
	private Boolean isActive;

	@ManyToMany(mappedBy="categories", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@OrderBy("name")
	private List<Product> products;

}