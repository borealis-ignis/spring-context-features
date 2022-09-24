package org.borealis.scf.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * @author Sergey Kastalski
 */
@Getter
@Setter
public class City {

	@Null
	private Long id;

	@NotNull
	@Size(min = 2, max = 20)
	private String name;

	@Null
	private Boolean capital;

	@Null
	@Min(-180)
	@Max(180)
	private Double longitude;

	@Null
	@Min(-90)
	@Max(90)
	private Double latitude;

}
