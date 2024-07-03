package fr.diginamic.hello.classes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Représente une ville avec un nom, un nombre d'habitants et un identifiant
 * unique.
 */
@Entity
public class Ville {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "NOM")
	@NotNull(message = "Le nom ne peut pas être nul")
	@Size(min = 1, max = 100)
	private String nom;

	@Column(name = "NB_HABITANTS")
	@NotNull
	private int nbHabitants;

	@ManyToOne
	@JoinColumn(name = "DEPARTEMENT_ID")
	private Departement departement;

	/**
	 * Constructeur par défaut requis par JPA.
	 */
	public Ville() {
	}

	/**
	 * Constructeur pour créer une ville avec un nom et un nombre d'habitants.
	 *
	 * @param nom         le nom de la ville
	 * @param nbHabitants le nombre d'habitants de la ville
	 */
	public Ville(String nom, int nbHabitants) {
		this.nom = nom;
		this.nbHabitants = nbHabitants;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbHabitants() {
		return nbHabitants;
	}

	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

	public int getId() {
		return id;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
		departement.addVille(this);
	}

}
