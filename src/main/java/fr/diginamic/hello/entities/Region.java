package fr.diginamic.hello.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a geographical region, identified by a unique code and name. A
 * region can encompass multiple departments.
 */
@Entity
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "CODE")
	@NotNull
	@Size(min = 1, max = 10)
	private String code;

	@Column(name = "NOM")
	@NotNull
	@Size(min = 1, max = 100)
	private String nom;

	@OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
	private Set<Departement> departements = new HashSet<>();

	/**
	 * Default constructor for JPA.
	 */
	public Region() {
	}

	/**
	 * Constructs a new Region with the specified code and name.
	 * 
	 * @param code The unique code identifying the region.
	 * @param nom  The name of the region.
	 */
	public Region(@NotNull @Size(min = 1, max = 10) String code, @NotNull @Size(min = 1, max = 100) String nom) {
		this.code = code;
		this.nom = nom;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<Departement> getDepartements() {
		return departements;
	}

	public void setDepartements(Set<Departement> departements) {
		this.departements = departements;
	}

	public long getId() {
		return id;
	}

}
