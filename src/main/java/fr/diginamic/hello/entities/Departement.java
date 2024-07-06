package fr.diginamic.hello.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a department entity within the context of a geographical
 * classification. A department is part of a larger region and is identified
 * uniquely by its code.
 * 
 * @Entity This annotation specifies that the class is an entity and is mapped
 *         to a database table.
 */
@Entity
public class Departement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "CODE")
	@NotNull
	@Size(min = 1, max = 10)
	private String code;

	/**
	 * The region to which the department belongs. This is mapped as a many-to-one
	 * relationship where multiple departments can be part of one region.
	 */
	@ManyToOne
	@JoinColumn(name = "REGION_ID")
	@JsonIgnore
	private Region region;

	/**
	 * Default constructor for JPA use.
	 */

	public Departement() {
	}

	/**
	 * Constructs a new Department with the specified code and parent region.
	 *
	 * @param code   the unique code of the department
	 * @param region the region to which the department belongs
	 */
	public Departement(String code, Region region) {
		this.code = code;
		this.region = region;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Departement [id=" + id + ", code=" + code + ", region=" + region + "]";
	}

}
