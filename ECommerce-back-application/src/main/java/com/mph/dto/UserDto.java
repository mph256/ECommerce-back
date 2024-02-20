package com.mph.dto;

import java.util.List;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@RequiredArgsConstructor(staticName="of")
@Getter
@Setter
@EqualsAndHashCode(of={"username"})
@ToString
public class UserDto {

	@Size(min=3, max=20)
	@NotBlank
	@NonNull
	@Schema(description="Username of the user")
	private String username;

	@Schema(description="First name of the user")
	private String firstname;

	@Schema(description="Last name of the user")
	private String lastname;

	@Size(min=3, max=64)
	@NotBlank
	@NonNull
	@Schema(description="Email of the user")
	private String email;

	@Schema(description="Phone of the user")
	private String phone;

	@Schema(description="Shipping address of the user")
	private AddressDto shippingAddress;

	@Schema(description="Billing address of the user")
	private AddressDto billingAddress;

	@NotEmpty
	@NonNull
	@ArraySchema(minItems=1, uniqueItems=true, arraySchema=@Schema(description="Roles of the user"), schema=@Schema(description="Role"))
	private List<String> roles;

}