package org.borealis.scf.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Sergey Kastalski
 */
@Entity
@Getter
@Setter
@ToString
public class CityDbo {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private Boolean capital;

	@Column
	private Double longitude;

	@Column
	private Double latitude;

}
