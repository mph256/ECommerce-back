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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OrderBy;

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
@Table(name="product")
@NoArgsConstructor
@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"id"})
@ToString
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(length=50, nullable=false)
	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	private String name;

	@Column(nullable=false)
	@Size(min=3)
	@NotBlank
	@NonNull
	private String description;

	@Column(length=100)
	private String dimensions;

	private Float weight;

	@Column(name="country_origin", length=52, nullable=false)
	@Size(min=3, max=52)
	@NotBlank
	@NonNull
	private String countryOfOrigin;

	@Column(length=50, nullable=false)
	@Size(min=3, max=50)
	@NotBlank
	@NonNull
	private String manufacturer;

	@Column(name="quantity_available", nullable=false)
	@Min(0)
	@NonNull
	private Integer quantityAvailable;

	@Column(nullable=false)
	@Min(1)
	@NonNull
	private Float price;

	@Column(nullable=false)
	@Min(0)
	@Max(5)
	@NonNull
	private Float rating;

	@Temporal(TemporalType.DATE)
	@Column(name="creation_date", nullable=false)
	@NonNull
	private Date creationDate;

	@Temporal(TemporalType.DATE)
	@Column(name="last_update", nullable=false)
	@NonNull
	private Date lastUpdate;

	@Column(name="is_active", nullable=false)
	@NonNull
	private Boolean isActive;

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name="seller_id", nullable=false)
	@NonNull
	private User seller;

	@OneToMany(targetEntity=Image.class, mappedBy="product", orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@NonNull
	private List<Image> images;

	@OneToMany(targetEntity=Review.class, mappedBy="product", orphanRemoval=true, cascade=CascadeType.ALL)
	@OrderBy("publicationDate DESC")
	private List<Review> reviews;

	@ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinTable(
		name="product_category",
		joinColumns=@JoinColumn(name="product_id"),
		inverseJoinColumns=@JoinColumn(name="category_id")
	)
	@OrderBy("name")
	@NonNull
	private List<Category> categories;

	public Product(long id) {
		this.id = id;
	}

	public void addImage(Image image) {
		images.add(image);
	}

	public void clearImages() {
		images.clear();
	}

	public void addReviews(List<Review> reviews) {
		this.reviews.addAll(reviews);
	}

}